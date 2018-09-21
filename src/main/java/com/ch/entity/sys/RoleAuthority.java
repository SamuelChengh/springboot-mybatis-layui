package com.ch.entity.sys;

import com.ch.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoleAuthority extends BaseEntity{

    private Integer roleId;

    private Integer authorityId;
}
