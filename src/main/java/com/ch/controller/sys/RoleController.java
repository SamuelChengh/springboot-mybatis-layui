package com.ch.controller.sys;

import com.ch.dto.sys.RoleDto;
import com.ch.entity.sys.Role;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.service.sys.RoleService;
import com.ch.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色列表
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
        return "role/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponsePageResult list(RoleDto dto){

        ResponsePageResult resp = roleService.list(dto);
        return resp;
    }

    /**
     * 新增角色
     *
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(){
        return "role/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult add(Role form){

        ResponseResult resp = roleService.add(form);
        return resp;
    }

    /**
     * 编辑角色
     *
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(){
        return "role/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(Role form){

        ResponseResult resp = roleService.update(form);
        return resp;
    }

    /**
     * 删除角色
     *
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(Role form){

        ResponseResult resp = roleService.delete(form);
        return resp;
    }

    @RequestMapping(value = "/getRole", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getRole(){

        ResponseResult resp = roleService.getRole();
        return resp;
    }

    /**
     * 角色权限
     *
     */
    @RequestMapping(value = "/getAuthority", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuVo> getAuthority(Integer roleId){
        return roleService.getAuthority(roleId);
    }

    /**
     * 设置权限
     *
     */
    @RequestMapping(value = "/updateAuthority", method = RequestMethod.GET)
    public String authority(){
        return "role/authority";
    }

    @RequestMapping(value = "/updateAuthority", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateAuthority(RoleDto dto){

        ResponseResult resp = roleService.updateAuthority(dto);
        return resp;
    }
}
