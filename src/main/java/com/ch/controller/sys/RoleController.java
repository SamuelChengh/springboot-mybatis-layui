package com.ch.controller.sys;

import com.ch.entity.sys.Role;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.service.sys.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "role/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponsePageResult list(Role form) {
        return roleService.list(form);
    }

    /**
     * 新增角色
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "role/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult add(Role form) {
        return roleService.add(form);
    }

    /**
     * 编辑角色
     */
    @RequestMapping(value = "/update/{roleId}", method = RequestMethod.GET)
    public String update(@PathVariable Integer roleId, Model model) {

        Role role = roleService.findById(roleId);
        model.addAttribute("role", role);

        return "role/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(Role form) {
        return roleService.update(form);
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(Role form) {
        return roleService.delete(form);
    }

    @RequestMapping(value = "/getRole", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getRole() {
        return roleService.getRole();
    }

    /**
     * 角色授权
     */
    @RequestMapping(value = "/authority", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult authority() {
        return roleService.authority();
    }

    /**
     * 角色授权--已选中的权限
     */
    @RequestMapping(value = "/authority/checked", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult authorityChecked(Integer roleId) {
        return roleService.authorityChecked(roleId);
    }
}
