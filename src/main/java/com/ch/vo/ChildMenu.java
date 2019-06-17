package com.ch.vo;

import lombok.Data;

@Data
public class ChildMenu {

	private Integer	id;
	private String	name;
	private String	icon;
	private Boolean	checked = false;
	private String	pageUrl;
}
