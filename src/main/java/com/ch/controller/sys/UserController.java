package com.ch.controller.sys;

import com.ch.entity.sys.User;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.service.sys.UserService;
import com.ch.vo.nav.NavVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String list() {
        return "user/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponsePageResult list(User form) {
        return userService.list(form);
    }

    /**
     * 新增用户
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "user/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult add(User form) {
        return userService.add(form);
    }

    /**
     * 编辑用户
     */
    @RequestMapping(value = "/update/{userId}", method = RequestMethod.GET)
    public String update(@PathVariable Integer userId, Model model) {

        User user = userService.findById(userId);
        model.addAttribute("user", user);

        return "user/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(User form) {
        return userService.update(form);
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(User form) {
        return userService.delete(form);
    }

    /**
     * 用户菜单
     */
    @RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
    @ResponseBody
    public List<NavVo> getMenuList(HttpServletRequest request) {
        return userService.getMenuList(request);
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.GET)
    public String resetPwd() {
        return "user/password";
    }

    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult resetPwd(HttpServletRequest request) {
        return userService.resetPwd(request);
    }
}
