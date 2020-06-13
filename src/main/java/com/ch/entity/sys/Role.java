package com.ch.entity.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ch.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_role")
public class Role extends BaseEntity {

    private String roleName;    // 名称

    private String roles;       // 授权标识

    private String remark;      // 描述

    @TableField(exist = false)
    private List<Menu> menus;   // 菜单

    @JsonIgnore
    @TableField(exist = false)
    private String menuIds;

}
