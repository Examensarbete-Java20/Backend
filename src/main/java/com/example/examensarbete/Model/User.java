package com.example.examensarbete.Model;

import com.example.examensarbete.Repositories.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.examensarbete.Model.Role.RoleConstant.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User implements UserDetails {

    @Id
    private String ID;
    private String googleId;
    private String username;
    private String email;
    private boolean isAdmin;
    private List<Role> roleList;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roleList == null) {
            return new ArrayList<>();
        } else {
            return this.roleList.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void addDefaultRole(RoleRepository roleRepository) {
        if (this.roleList == null) {
            this.roleList = new ArrayList<>();
            roleRepository.findByName(USER)
                    .ifPresentOrElse(role -> this.roleList.add(role), () -> {
                        this.roleList.add(roleRepository.save(new Role(USER)));
                    } );
            log.info("create array and adding ROLE_CUSTOMER to new user");
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you can't send a list of roles when you create or update a customer");
        }

    }

    public void addRoleToUser(String roleName, RoleRepository roleRepository) {
        if(this.roleList == null) {
            this.roleList = new ArrayList<>();
        }
        Role saveRole = roleRepository.save(new Role(valueOf(roleName)));
        this.roleList.add(saveRole);
    }
}
