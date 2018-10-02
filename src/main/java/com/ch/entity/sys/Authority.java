package com.ch.entity.sys;

import com.ch.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Authority extends BaseEntity{

    private Integer id;

    private String  name;        // 名称

    private String  authUrl;     // 路径

    private Integer parent;      // 父节点

    private Integer displaySort; // 排序

    private Integer displayType; // 类型 1:模块 2:菜单

    private String  remark;      // 备注
}
