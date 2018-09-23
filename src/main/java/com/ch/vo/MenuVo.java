package com.ch.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuVo {

	private Integer         parentId;
	private String          parentName;
	private Boolean         checked;
	private List<ChildMenu> childMenus;
}
