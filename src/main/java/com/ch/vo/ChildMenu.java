package com.ch.vo;

import lombok.Data;

@Data
public class ChildMenu {

	private Integer  id;
	private String   name;
	private Boolean  checked = false;
	private String   pageUrl;
}
