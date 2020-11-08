package com.ch.vo.nav;

import lombok.Data;

import java.util.List;

@Data
public class NavVo {

    private Integer parentId;

    private String parentName;

    private String parentIcon;

    private Boolean checked;

    private List<ChildNav> childMenus;

}
