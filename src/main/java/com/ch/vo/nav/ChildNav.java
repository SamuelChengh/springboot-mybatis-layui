package com.ch.vo.nav;

import lombok.Data;

@Data
public class ChildNav {

    private Integer id;

    private String name;

    private String icon;

    private Boolean checked = false;

    private String pageUrl;

}
