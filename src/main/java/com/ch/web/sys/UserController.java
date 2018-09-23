package com.ch.web.sys;

import com.ch.dto.user.UserDto;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.service.sys.UserService;
import com.ch.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    /*
    * 用户列表
    *
    * */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponsePageResult list(@RequestBody UserDto dto){

        ResponsePageResult resp = userService.list(dto);
        return resp;
    }

    /*
     * 新增用户
     *
     * */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody UserDto dto){

        ResponseResult resp = userService.add(dto);
        return resp;
    }

    /*
     * 编辑用户
     *
     * */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody UserDto dto){

        ResponseResult resp = userService.update(dto);
        return resp;
    }

    /*
     * 删除用户
     *
     * */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(@RequestBody UserDto dto){

        ResponseResult resp = userService.delete(dto);
        return resp;
    }

    /**
     * 用户菜单
     *
     */
    @RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
    public List<MenuVo> getMenuList(){

        List<MenuVo> list = new ArrayList();

        return list;
    }
}
