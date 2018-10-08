package com.ch.service.sys;

import com.ch.common.ConstantsCMP;
import com.ch.dao.sys.RoleDao;
import com.ch.dao.sys.UserDao;
import com.ch.dto.sys.UserDto;
import com.ch.entity.sys.Authority;
import com.ch.entity.sys.Role;
import com.ch.entity.sys.User;
import com.ch.response.*;
import com.ch.utils.EncryptUtil;
import com.ch.vo.ChildMenu;
import com.ch.vo.MenuVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public ResponsePageResult list(UserDto dto) {
        Integer page = dto.getPage();
        Integer limit = dto.getLimit();

        Page<User> pages = PageHelper.startPage(page, limit).doSelectPage(()-> userDao.findAll(dto));

        return RestResultGenerator.createSuccessPageResult(pages);
    }

    public ResponseResult add(UserDto dto) {

        if(userDao.findByLoginName(dto.getLoginName()) != null){
            return RestResultGenerator.createErrorResult(ResponseEnum.ACCOUNT_EXIST);
        }

        User user = new User();
        user.setLoginName(dto.getLoginName());
        user.setNickName(dto.getNickName());
        user.setPassword(EncryptUtil.encryptMD5(dto.getPassword()));
        user.setStatus(dto.getStatus());
        user.setRemark(dto.getRemark());
        userDao.insert(user);

        if(dto.getRoleIds() != null && dto.getRoleIds().size() > 0){
            Map<String, Object> map = new HashMap();
            map.put("userId", user.getId());
            map.put("roleIds", dto.getRoleIds());
            userDao.insertUserRole(map);
        }

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult update(UserDto dto) {

        User user = userDao.findById(dto.getId());
        if(!user.getLoginName().equals(dto.getLoginName())){
            if(userDao.findByLoginName(dto.getLoginName()) != null){
                return RestResultGenerator.createErrorResult(ResponseEnum.ACCOUNT_EXIST);
            }
        }

        user.setLoginName(dto.getLoginName());
        user.setNickName(dto.getNickName());
        if(!StringUtils.isEmpty(dto.getPassword())){
            if(!dto.getPassword().equals(user.getPassword())){
                user.setPassword(EncryptUtil.encryptMD5(dto.getPassword()));
            }
        }
        user.setStatus(dto.getStatus());
        user.setRemark(dto.getRemark());
        userDao.update(user);

        if(dto.getRoleIds() != null && dto.getRoleIds().size() > 0){
            userDao.deleteByUserId(dto.getId());

            Map<String, Object> map = new HashMap();
            map.put("userId", dto.getId());
            map.put("roleIds", dto.getRoleIds());
            userDao.insertUserRole(map);
        }

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult delete(UserDto dto) {

        User user = userDao.findById(dto.getId());
        if(user == null){
            return RestResultGenerator.createErrorResult(ResponseEnum.USER_NOT_EXIST);
        }
        if(user.getRoles().size() > 0){
            return RestResultGenerator.createErrorResult(ResponseEnum.DATA_RELATED);
        }

        userDao.delete(dto.getId());
        userDao.deleteByUserId(dto.getId());

        return RestResultGenerator.createSuccessResult();
    }

    public List<MenuVo> getMenuList(HttpServletRequest request) {

        List<MenuVo> list = new ArrayList();

        User user = ConstantsCMP.getSessionUser(request);
        List<Role> roles = user.getRoles();
        if(roles != null && roles.size() > 0){
            for(Role role : roles){
                Role r = roleDao.findById(role.getId());
                List<Authority> authorities = r.getAuthorities();

                List<Authority> parentMenuList = new ArrayList();   //  父节点菜单
                List<Authority> childMenuList = new ArrayList();    //  子节点菜单
                for(Authority authority : authorities){
                    if(authority.getParent().equals(0)){
                        parentMenuList.add(authority);
                    }else{
                        childMenuList.add(authority);
                    }
                }

                for(Authority parentMenu : parentMenuList){
                    MenuVo vo = new MenuVo();
                    vo.setParentId(parentMenu.getId());
                    vo.setParentName(parentMenu.getName());
                    List<ChildMenu> childList = new ArrayList();
                    for(Authority childMenu : childMenuList){
                        if(childMenu.getParent().equals(parentMenu.getId())){
                            ChildMenu child = new ChildMenu();
                            child.setId(childMenu.getId());
                            child.setName(childMenu.getName());
                            child.setPageUrl(childMenu.getAuthUrl());
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

    public ResponseResult resetPwd(HttpServletRequest request) {

        String pwd = request.getParameter("pwd").trim();
        String npwd = request.getParameter("npwd").trim();
        String rpwd = request.getParameter("rpwd").trim();

        User user = ConstantsCMP.getSessionUser(request);
        // 验证当前密码是否正确
        if(!user.getPassword().equals(EncryptUtil.encryptMD5(pwd))){
            return RestResultGenerator.createErrorResult(ResponseEnum.UNKNOWN, "原密码输入不正确!");
        }

        // 验证验证新密码是否相等
        if(!npwd.equals(rpwd)){
            return RestResultGenerator.createErrorResult(ResponseEnum.UNKNOWN, "新密码两次输入不一致!");
        }

        // 修改密码
        user.setPassword(EncryptUtil.encryptMD5(npwd));
        userDao.update(user);

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
