package com.ch.service.sys;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch.common.ConstantsCMP;
import com.ch.dao.sys.RoleDao;
import com.ch.dao.sys.UserDao;
import com.ch.entity.sys.Menu;
import com.ch.entity.sys.Role;
import com.ch.entity.sys.User;
import com.ch.entity.sys.UserRole;
import com.ch.response.ResponseEnum;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.response.RestResultGenerator;
import com.ch.utils.EncryptUtil;
import com.ch.vo.nav.ChildNav;
import com.ch.vo.nav.NavVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService extends ServiceImpl<UserDao, User> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public User findById(Integer userId) {
        return userDao.findById(userId);
    }

    public ResponsePageResult list(User form) {

        Integer page = form.getPage();
        Integer limit = form.getLimit();

        Page<User> pages = PageHelper.startPage(page, limit).doSelectPage(() -> userDao.findAll(form));

        return RestResultGenerator.createSuccessPageResult(pages);
    }

    public ResponseResult add(User form) {

        if (userDao.findByUserName(form.getUserName()) != null) {
            return RestResultGenerator.createErrorResult(ResponseEnum.USER_NAME_EXIST);
        }

        form.setPassword(EncryptUtil.encryptMD5(form.getPassword()));
        form.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        userDao.insert(form);

        if (StringUtils.isNotBlank(form.getRoleIds())) {
            List<UserRole> list = new ArrayList<>();

            String[] roleIds = form.getRoleIds().split(",");
            for (String roleId : roleIds) {
                UserRole ur = new UserRole();
                ur.setUserId(form.getId());
                ur.setRoleId(Integer.valueOf(roleId));
                list.add(ur);
            }

            userDao.batchInsertUserRole(list);
        }

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult update(User form) {

        User user = userDao.findById(form.getId());
        if (!user.getUserName().equals(form.getUserName())) {
            if (userDao.findByUserName(form.getUserName()) != null) {
                return RestResultGenerator.createErrorResult(ResponseEnum.USER_NAME_EXIST);
            }
        }

        if (StringUtils.isNotBlank(form.getPassword())) {
            if (!form.getPassword().equals(user.getPassword())) {
                form.setPassword(EncryptUtil.encryptMD5(form.getPassword()));
            }
        }
        form.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        userDao.updateById(form);

        if (StringUtils.isNotBlank(form.getRoleIds())) {
            userDao.deleteByUserId(user.getId());

            List<UserRole> list = new ArrayList<>();

            String[] roleIds = form.getRoleIds().split(",");
            for (String roleId : roleIds) {
                UserRole ur = new UserRole();
                ur.setUserId(form.getId());
                ur.setRoleId(Integer.valueOf(roleId));
                list.add(ur);
            }

            userDao.batchInsertUserRole(list);
        }

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult delete(User form) {

        User user = userDao.findById(form.getId());
        if (user == null) {
            return RestResultGenerator.createErrorResult(ResponseEnum.USER_NOT_EXIST);
        }

        userDao.deleteById(user.getId());
        userDao.deleteByUserId(user.getId());

        return RestResultGenerator.createSuccessResult();
    }

    public List<NavVo> getMenuList(HttpServletRequest request) {

        List<NavVo> list = new ArrayList();

        User user = ConstantsCMP.getSessionUser(request);
        List<Role> roles = user.getRoles();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                Role r = roleDao.findById(role.getId());
                List<Menu> authorities = r.getMenus();

                List<Menu> parentMenuList = new ArrayList();   //  父节点菜单
                List<Menu> childMenuList = new ArrayList();    //  子节点菜单
                for (Menu authority : authorities) {
                    if (authority.getParentId().equals(0)) {
                        parentMenuList.add(authority);
                    } else {
                        childMenuList.add(authority);
                    }
                }

                for (Menu parentMenu : parentMenuList) {
                    NavVo vo = new NavVo();
                    vo.setParentId(parentMenu.getId());
                    vo.setParentName(parentMenu.getMenuName());
                    vo.setParentIcon(parentMenu.getIcon());
                    List<ChildNav> childList = new ArrayList();
                    for (Menu childMenu : childMenuList) {
                        if (childMenu.getParentId().equals(parentMenu.getId())) {
                            ChildNav child = new ChildNav();
                            child.setId(childMenu.getId());
                            child.setName(childMenu.getMenuName());
                            child.setIcon(childMenu.getIcon());
                            child.setPageUrl(childMenu.getMenuUrl());
                            childList.add(child);
                        }
                    }
                    vo.setChildMenus(childList);
                    list.add(vo);
                }
            }
        }

        return list;
    }

    /**
     * 修改密码
     */
    public ResponseResult resetPwd(HttpServletRequest request) {

        String pwd = request.getParameter("pwd").trim();
        String npwd = request.getParameter("npwd").trim();
        String rpwd = request.getParameter("rpwd").trim();

        User user = ConstantsCMP.getSessionUser(request);
        // 验证当前密码是否正确
        if (!user.getPassword().equals(EncryptUtil.encryptMD5(pwd))) {
            return RestResultGenerator.createErrorResult(ResponseEnum.UNKNOWN, "原密码输入不正确!");
        }

        // 验证验证新密码是否相等
        if (!npwd.equals(rpwd)) {
            return RestResultGenerator.createErrorResult(ResponseEnum.UNKNOWN, "新密码两次输入不一致!");
        }

        // 修改密码
        user.setPassword(EncryptUtil.encryptMD5(npwd));
        userDao.updateById(user);

        // 退出系统
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        return RestResultGenerator.createSuccessResult();
    }

}
