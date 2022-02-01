package com.example.examensarbete.security.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * A class with the DTO for the roles
 */
@Getter
public class RoleDto {
    private final String name;

    @JsonCreator
    public RoleDto(@JsonProperty("name") String name) {
        this.name = name;
    }
}