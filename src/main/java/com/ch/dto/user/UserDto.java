package com.ch.dto.user;

import com.ch.entity.sys.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto extends User{

    private List<Integer> roleIds;
    private Integer page;
    private Integer limit;

}
