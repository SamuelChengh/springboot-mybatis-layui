package com.ch.entity.sys;

import com.ch.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Role extends BaseEntity{

    private String  name;   // 名称

    private String  remark; // 备注

    private List<Authority> authorities;
}
