package com.ch.dto.sys;

import com.ch.entity.sys.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto extends User{

    private String  roleIds;
    private Integer page;
    private Integer limit;
}
