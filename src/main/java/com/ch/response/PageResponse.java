package com.ch.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class PageResponse<T> implements Serializable {

    private Integer  code;

    private String   message;

    private Long     count;

    private List<T>  data;
}
