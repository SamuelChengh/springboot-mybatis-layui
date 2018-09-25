package com.ch.dto.sys;

import com.ch.entity.sys.Authority;
import lombok.Data;

@Data
public class AuthorityDto extends Authority{

    private Integer page;
    private Integer limit;
}
