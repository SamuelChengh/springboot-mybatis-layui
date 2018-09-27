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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Page<User> pages = PageHelper.startPage(page, limit).doSelectPage(()-> userDao.findAll());

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
        user.setPassword(EncryptUtil.encryptMD5(dto.getPassword()));
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
}
