package com.issoft.cinemaapplication.config;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(innerTypeName = "Values")
public enum Role {
    ;
    private String ROLE_USER, ROLE_ADMIN;
}
