package com.ch.controller.sys;

import com.ch.entity.sys.Menu;
import com.ch.response.ResponseResult;
import com.ch.service.sys.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 菜单列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "menu/list";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult list(Menu form) {
        return menuService.list();
    }

    /**
     * 新增菜单
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "menu/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult add(Menu form) {
        return menuService.add(form);
    }

    /**
     * 编辑菜单
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(Menu form) {
        return menuService.update(form);
    }

    /**
     * 删除菜单
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(Menu form) {
        return menuService.delete(form);
    }

    /**
     * 获取父节点菜单
     */
    @RequestMapping(value = "/getParent", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getParent(Integer type) {
        return menuService.getParent(type);
    }
}
