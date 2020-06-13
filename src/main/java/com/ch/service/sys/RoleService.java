package com.ch.service.sys;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch.dao.sys.MenuDao;
import com.ch.dao.sys.RoleDao;
import com.ch.dao.sys.UserDao;
import com.ch.entity.sys.Menu;
import com.ch.entity.sys.Role;
import com.ch.entity.sys.RoleMenu;
import com.ch.response.ResponseEnum;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.response.RestResultGenerator;
import com.ch.vo.auth.AuthTreeVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleService extends ServiceImpl<RoleDao, Role> {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private UserDao userDao;

    public ResponsePageResult list(Role form) {

        Integer page = form.getPage();
        Integer limit = form.getLimit();

        Page<Role> pages = PageHelper.startPage(page, limit).doSelectPage(() -> roleDao.findAll(form));

        return RestResultGenerator.createSuccessPageResult(pages);
    }

    public ResponseResult add(Role form) {

        if (roleDao.findByRoleName(form.getRoleName()) != null) {
            return RestResultGenerator.createErrorResult(ResponseEnum.ROLE_NAME_EXIST);
        }
        form.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        roleDao.insert(form);

        if (StringUtils.isNotBlank(form.getMenuIds())) {
            List<RoleMenu> list = new ArrayList<>();

            String[] menuIds = form.getMenuIds().split(",");
            for (String menuId : menuIds) {
                RoleMenu rm = new RoleMenu();
                rm.setRoleId(form.getId());
                rm.setMenuId(Integer.valueOf(menuId));
                list.add(rm);
            }

            roleDao.batchInsertRoleMenu(list);
        }

        return RestResultGenerator.createSuccessResult();
    }

    public Role findById(Integer roleId) {
        return roleDao.findById(roleId);
    }

    public ResponseResult update(Role form) {

        Role role = roleDao.findById(form.getId());
        if (!role.getRoleName().equals(form.getRoleName())) {
            if (roleDao.findByRoleName(form.getRoleName()) != null) {
                return RestResultGenerator.createErrorResult(ResponseEnum.ROLE_NAME_EXIST);
            }
        }
        form.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        roleDao.updateById(form);

        if (StringUtils.isNotBlank(form.getMenuIds())) {
            roleDao.deleteByRoleId(role.getId());

            List<RoleMenu> list = new ArrayList<>();

            String[] menuIds = form.getMenuIds().split(",");
            for (String menuId : menuIds) {
                RoleMenu rm = new RoleMenu();
                rm.setRoleId(form.getId());
                rm.setMenuId(Integer.valueOf(menuId));
                list.add(rm);
            }

            roleDao.batchInsertRoleMenu(list);
        }

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult delete(Role form) {

        Role role = roleDao.findById(form.getId());
        if (role == null) {
            return RestResultGenerator.createErrorResult(ResponseEnum.USER_NOT_EXIST);
        }

        roleDao.deleteById(form.getId());
        roleDao.deleteByRoleId(form.getId());

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult getRole() {

        List<Map<String, Object>> mapList = new ArrayList();

        List<Role> list = roleDao.selectList(null);
        Map<String, Object> jsonMap;
        for (Role role : list) {
            jsonMap = new HashMap();
            jsonMap.put("value", role.getId());
            jsonMap.put("name", role.getRoleName());
            jsonMap.put("roles", role.getRoles());
            mapList.add(jsonMap);
        }

        return RestResultGenerator.createSuccessResult(mapList);
    }

    public ResponseResult authority() {

        // 最终返回的结果
        List<AuthTreeVo> menuList = new ArrayList<>();

        // 原始的数据
        List<Menu> rootMenu = menuDao.findAll(null);

        // 模块: 一级菜单(parentId=0)
        for (Menu menu : rootMenu) {
            if (menu.getParentId().equals(0)) {
                AuthTreeVo vo = new AuthTreeVo();
                vo.setId(menu.getId());
                vo.setTitle(menu.getMenuName());
                vo.setSpread(true);
                menuList.add(vo);
            }
        }

        // 设置子菜单(递归调用)
        for (AuthTreeVo menu : menuList) {
            menu.setChildren(getChild(menu.getId(), rootMenu));
        }

        return RestResultGenerator.createSuccessResult(menuList);
    }

    /**
     * 递归查找子菜单
     */
    private List<AuthTreeVo> getChild(Integer id, List<Menu> rootMenu) {

        // 子菜单
        List<AuthTreeVo> childList = new ArrayList<>();

        for (Menu menu : rootMenu) {
            // 将父菜单id与传过来的id比较
            if (menu.getParentId().equals(id)) {
                AuthTreeVo vo = new AuthTreeVo();
                vo.setId(menu.getId());
                vo.setTitle(menu.getMenuName());
                childList.add(vo);
            }
        }

        // 把子菜单的子菜单再循环一遍
        for (AuthTreeVo menu : childList) {
            menu.setChildren(getChild(menu.getId(), rootMenu));
        }

        // 递归退出
        if (childList.size() == 0) {
            return null;
        }

        return childList;
    }

    /**
     * 由于layui,tree组件存在bug,数据源参数checked在父子节点同时设定时存在问题
     * 只取子菜单的id赋值给前端
     */
    public ResponseResult authorityChecked(Integer roleId) {

        // 按照parentId分组
        Map<Integer, List<Menu>> menuMap = new HashMap<>();
        List<Menu> menuList = menuDao.findAll(null);
        for (Menu menu : menuList) {
            Integer key = menu.getParentId();
            List<Menu> list = menuMap.get(key);
            if (list == null) {
                list = new ArrayList<>();
                list.add(menu);
                menuMap.put(key, list);
            } else {
                list.add(menu);
            }
        }

        List<Integer> checkedList = new ArrayList<>();

        Role role = roleDao.findById(roleId);
        List<Menu> menus = role.getMenus();
        if (menus != null && menus.size() > 0) {
            for (Menu menu : menus) {
                List<Menu> list = menuMap.get(menu.getId());
                if (list == null) {
                    checkedList.add(menu.getId());
                }
            }
        }

        return RestResultGenerator.createSuccessResult(checkedList);
    }
}
