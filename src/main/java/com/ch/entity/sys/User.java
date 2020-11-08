package com.ch.entity.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ch.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_user")
public class User extends BaseEntity {

    private String userName;    // 用户名

    private String nickName;    // 昵称

    private String password;    // 密码

    private Integer status;     // 状态 1:开启 0:禁用

    private String remark;      // 备注

    @TableField(exist = false)
    private List<Role> roles;   // 角色

    @JsonIgnore
    @TableField(exist = false)
    private String roleIds;     // 前端保存角色时，传的参数

    @JsonIgnore
    @TableField(exist = false)
    private Integer roleId;     // 前端查询角色时，传的参数

}
