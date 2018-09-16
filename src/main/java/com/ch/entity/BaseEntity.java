package com.ch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@ToString
public abstract class BaseEntity extends IdEntity implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Timestamp createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Timestamp updatedDate;
    protected Integer status;
}
