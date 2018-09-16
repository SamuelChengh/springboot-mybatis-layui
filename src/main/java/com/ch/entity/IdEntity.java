package com.ch.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public abstract class IdEntity implements Serializable {
    protected Long id;
}
