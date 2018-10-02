package com.ch.web.sys;

import com.ch.entity.sys.Authority;
import com.ch.response.ResponseResult;
import com.ch.service.sys.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    /**
     * 菜单列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
        return "menu/list";
    }

    @RequestMapping(value="list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult list(Authority authority){

        ResponseResult resp = authorityService.list();
        return resp;
    }

    /**
     * 新增菜单
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(){
        return "menu/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult add(Authority authority){

        ResponseResult resp = authorityService.add(authority);
        return resp;
    }

    /**
     * 编辑菜单
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(Authority authority){

        ResponseResult resp = authorityService.update(authority);
        return resp;
    }

    /**
     * 删除菜单
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(@RequestBody Authority authority){

        ResponseResult resp = authorityService.delete(authority);
        return resp;
    }

    /**
     * 获取父节点菜单
     *
     */
    @RequestMapping(value = "/getParent", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getParent() {

        ResponseResult resp = authorityService.getParent();
        return resp;
    }
}
