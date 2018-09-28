package com.ch.web.sys;

import com.ch.dto.sys.RoleDto;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.service.sys.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponsePageResult list(RoleDto dto){

        ResponsePageResult resp = roleService.list(dto);
        return resp;
    }

    /**
     * 新增角色
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult add(RoleDto dto){

        ResponseResult resp = roleService.add(dto);
        return resp;
    }

    /**
     * 编辑角色
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(RoleDto dto){

        ResponseResult resp = roleService.update(dto);
        return resp;
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(RoleDto dto){

        ResponseResult resp = roleService.delete(dto);
        return resp;
    }

    @RequestMapping(value = "/getRole", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getRole(){

        ResponseResult resp = roleService.getRole();
        return resp;
    }
}
