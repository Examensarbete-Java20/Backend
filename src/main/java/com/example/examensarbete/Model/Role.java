package com.example.examensarbete.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = AUTO)
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
