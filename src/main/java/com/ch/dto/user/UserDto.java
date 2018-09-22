package com.ch.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Integer id;
    private String  loginName;
    private String  nickName;
    private String  password;
    private Integer status;
    private String  remark;
    private List<Integer> roleIds;
    private Integer page;          // 页码
    private Integer limit;         // 页面条数
}
