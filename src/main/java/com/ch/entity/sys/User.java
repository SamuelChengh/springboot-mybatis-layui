package com.ch.entity.sys;

import com.ch.entity.BaseEntity;
import lombok.Data;

@Data
public class User extends BaseEntity {
    private String name;
    private Integer age;
}
