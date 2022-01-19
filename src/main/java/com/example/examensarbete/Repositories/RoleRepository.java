package com.example.examensarbete.Repositories;

import com.example.examensarbete.Model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(Role.RoleConstant name);
}
