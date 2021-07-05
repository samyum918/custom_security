package com.spring.customSecurity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String fullName;
    private String lastName;
    private String description;
}
