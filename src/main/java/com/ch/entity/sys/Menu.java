package com.ch.entity.sys;

import com.baomidou.mybatisplus.annotations.TableName;
import com.ch.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("sys_menu")
public class Menu extends BaseEntity {

    private String menuName;        // 菜单名称

    private String menuUrl;         // 菜单路径

    private Integer parentId;       // 父节点

    private Integer displaySort;    // 排序

    private Integer displayType;    // 类型 1:模块 2:菜单

    private String permission;      // 授权标识

    private String icon;            // 图标

    private String remark;          // 备注

}
