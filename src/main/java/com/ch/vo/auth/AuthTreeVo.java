package com.ch.vo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class AuthTreeVo {

    private Integer id;         // 节点唯一索引值

    private String  title;      // 节点标题

    private Boolean spread;     // 节点是否初始展开，默认false

    private Boolean checked;    // 节点是否初始为选中状态（如果开启复选框的话），默认false

    private List<AuthTreeVo>    children;   // 子节点

}
