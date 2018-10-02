package com.ch.web.sys;

import com.ch.dto.sys.UserDto;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.service.sys.UserService;
import com.ch.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    /**
    * 用户列表
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
        return "user/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponsePageResult list(UserDto dto){

        ResponsePageResult resp = userService.list(dto);
        return resp;
    }

    /**
     * 新增用户
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(){
        return "user/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult add(UserDto dto){

        ResponseResult resp = userService.add(dto);
        return resp;
    }

    /**
     * 编辑用户
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(){
        return "user/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(UserDto dto){

        ResponseResult resp = userService.update(dto);
        return resp;
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(UserDto dto){

        ResponseResult resp = userService.delete(dto);
        return resp;
    }

    /**
     * 用户菜单
     */
    @RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuVo> getMenuList(HttpServletRequest request){
        return userService.getMenuList(request);
    }

    /*
    * 修改密码
    * */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.GET)
    public String resetPwd(){
        return "user/password";
    }

    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult resetPwd(HttpServletRequest request){

        ResponseResult resp = userService.resetPwd(request);
        return resp;
    }
}
