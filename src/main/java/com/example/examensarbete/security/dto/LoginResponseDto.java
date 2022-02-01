package com.example.examensarbete.security.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO that sets the parameters for the login-response
 */
@Getter
public class LoginResponseDto {
    @JsonProperty("username")
    private final String username;
    @JsonProperty("roles")
    private final List<RoleDto> roles;
    @JsonProperty("jwt_token")
    private final String jwtToken;

    @JsonCreator
    public LoginResponseDto(@JsonProperty("username") String username,@JsonProperty("roles") List<RoleDto> roles,@JsonProperty("jwt_token") String jwtToken) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }

    /**
     * A list with the DTO for role
     * @param roles a collection with roles
     * @return list of roleDTO
     */
    public static List<RoleDto> convertSimpleGrantedAuthority(Collection<GrantedAuthority> roles) {
        return roles.stream()
                .map(role -> new RoleDto(role.getAuthority()))
                .collect(Collectors.toList());
    }
}