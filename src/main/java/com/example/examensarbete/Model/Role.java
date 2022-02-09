package com.example.examensarbete.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;


@Document
@NoArgsConstructor
@Data
public class Role {

    @Id
    private String id;

    private RoleConstant name;

    public Role(RoleConstant name) {
        this.name = name;
    }

    public enum RoleConstant {
        SUPER_ADMIN,
        ADMIN,
        USER
    }
}
