package com.ch.web.sys;

import com.ch.entity.sys.Authority;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.service.sys.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/authority", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    /**
     * 菜单列表
     */
    @RequestMapping(value="list", method = RequestMethod.POST)
    public ResponsePageResult list(){

        ResponsePageResult resp = authorityService.list();
        return resp;
    }

    /**
     * 新增菜单
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Authority authority){

        ResponseResult resp = authorityService.add(authority);
        return resp;
    }

    /**
     * 编辑菜单
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody Authority authority){

        ResponseResult resp = authorityService.update(authority);
        return resp;
    }

    /**
     * 删除菜单
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(@RequestBody Authority authority){

        ResponseResult resp = authorityService.delete(authority);
        return resp;
    }
}
