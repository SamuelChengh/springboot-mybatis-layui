package com.ch.service.sys;

import com.ch.dao.sys.AuthorityDao;
import com.ch.dao.sys.RoleDao;
import com.ch.dto.sys.RoleDto;
import com.ch.entity.sys.Authority;
import com.ch.entity.sys.Role;
import com.ch.response.ResponseEnum;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.response.RestResultGenerator;
import com.ch.vo.ChildMenu;
import com.ch.vo.MenuVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AuthorityDao authorityDao;

    public ResponsePageResult list(RoleDto dto) {
        Integer page = dto.getPage();
        Integer limit = dto.getLimit();

        Page<Role> pages = PageHelper.startPage(page, limit).doSelectPage(()-> roleDao.findAll(dto));

        return RestResultGenerator.createSuccessPageResult(pages);
    }


    public ResponseResult add(RoleDto dto) {

        if(roleDao.findByName(dto.getName()) != null){
            return RestResultGenerator.createErrorResult(ResponseEnum.NAME_EXIST);
        }

        Role role = new Role();
        role.setName(dto.getName());
        role.setRemark(dto.getRemark());
        roleDao.insert(role);

        return RestResultGenerator.createSuccessResult();
    }


    public ResponseResult update(RoleDto dto) {

        Role role = roleDao.findById(dto.getId());
        if(!role.getName().equals(dto.getName())){
            if(roleDao.findByName(dto.getName()) != null){
                return RestResultGenerator.createErrorResult(ResponseEnum.NAME_EXIST);
            }
        }

        role.setName(dto.getName());
        role.setRemark(dto.getRemark());
        roleDao.update(role);

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult delete(RoleDto dto) {

        Role role = roleDao.findById(dto.getId());
        if(role == null){
            return RestResultGenerator.createErrorResult(ResponseEnum.USER_NOT_EXIST);
        }

        roleDao.delete(dto.getId());
        roleDao.deleteByRoleId(dto.getId());

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult getRole() {

        List<Map<String, Object>> listMap = new ArrayList();

        List<Role> list = roleDao.findAll(null);
        Map<String, Object> jsonMap = null;
        for (Role role : list) {
            jsonMap = new HashMap();
            jsonMap.put("roleId", role.getId());
            jsonMap.put("roleName", role.getName());
            listMap.add(jsonMap);
        }

        return RestResultGenerator.createSuccessResult(listMap);
    }

    public List<MenuVo> getAuthority(Integer roleId) {

        List<MenuVo> list = new ArrayList();

        Role role = roleDao.findById(roleId);
        List<Authority> authorities = role.getAuthorities();

        List<Authority> menuList = authorityDao.findAll();
        if(menuList != null && menuList.size() > 0){
            List<Authority> parentMenuList = new ArrayList();   //  父节点菜单
            List<Authority> childMenuList = new ArrayList();    //  子节点菜单
            for(Authority menu : menuList){
                if(menu.getParent().equals(0)){
                    parentMenuList.add(menu);
                }else{
                    childMenuList.add(menu);
                }
            }

            for(Authority parentMenu : parentMenuList){
                MenuVo vo = new MenuVo();
                vo.setParentId(parentMenu.getId());
                vo.setParentName(parentMenu.getName());
                for(Authority myMenu : authorities){
                    if(myMenu.getId().equals(parentMenu.getId())){
                        vo.setChecked(true);
                        break;
                    }
                }
                List<ChildMenu> childList = new ArrayList();
                for(Authority childMenu : childMenuList){
                    if(childMenu.getParent().equals(parentMenu.getId())){
                        ChildMenu child = new ChildMenu();
                        child.setId(childMenu.getId());
                        child.setName(childMenu.getName());
                        for(Authority myMenu : authorities){
                            if(myMenu.getId().equals(childMenu.getId())){
                                child.setChecked(true);
                                break;
                            }
                        }
                        child.setPageUrl(childMenu.getAuthUrl());
                        childList.add(child);
                    }
                }
                vo.setChildMenus(childList);
                list.add(vo);
            }
        }

        return list;
    }

    public ResponseResult updateAuthority(RoleDto dto) {

        Role role = roleDao.findById(dto.getId());
        if(role == null){
            return RestResultGenerator.createErrorResult(ResponseEnum.USER_NOT_EXIST);
        }

        if(dto.getAuthIds() != null && dto.getAuthIds().size() > 0){
            roleDao.deleteByRoleId(dto.getId());

            Map<String, Object> map = new HashMap();
            map.put("roleId", dto.getId());
            map.put("authIds", dto.getAuthIds());
            roleDao.insertRoleAuth(map);
        }

        return RestResultGenerator.createSuccessResult();
    }
}
