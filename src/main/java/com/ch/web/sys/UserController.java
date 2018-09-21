package com.ch.web.sys;

import com.ch.response.ResponseResult;
import com.ch.service.sys.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize){

        ResponseResult resp = userService.list(pageNumber, pageSize);
        return resp;
    }
}
