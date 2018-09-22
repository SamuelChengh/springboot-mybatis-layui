package com.ch.entity.sys;

import com.ch.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User extends BaseEntity{

    private Integer id;

    private String  loginName; // 登录名

    private String  nickName;  // 昵称

    private String  password;  // 密码

    private String  salt;      // 密码盐

    private Integer status;    // 状态 1:开启 0:禁用

    private String  remark;    // 备注
}
