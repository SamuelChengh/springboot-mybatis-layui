package com.ch.dto.sys;

import com.ch.entity.sys.Role;
import lombok.Data;

import java.util.List;

@Data
public class RoleDto extends Role{

    private String     authIds;
    private Integer    page;
    private Integer    limit;
}
