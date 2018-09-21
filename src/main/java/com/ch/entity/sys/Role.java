package com.ch.entity.sys;

import com.ch.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Role extends BaseEntity{

    private Integer id;

    private String  name;   // 名称

    private String  remark; // 备注
}
