package com.ch.entity.sys;

import com.ch.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRole extends BaseEntity{

    private Integer userId;

    private Integer roleId;
}
