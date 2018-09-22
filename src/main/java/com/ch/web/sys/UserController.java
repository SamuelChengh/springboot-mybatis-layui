package com.ch.web.sys;

import com.ch.entity.sys.User;
import com.ch.response.ResponsePageResult;
import com.ch.service.sys.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponsePageResult list(User user){

        ResponsePageResult resp = userService.list(user);
        return resp;
    }
}
