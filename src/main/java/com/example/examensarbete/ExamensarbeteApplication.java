package com.example.examensarbete;

import com.example.examensarbete.Model.Role;
import com.example.examensarbete.Model.User;
import com.example.examensarbete.Repositories.RoleRepository;
import com.example.examensarbete.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ExamensarbeteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamensarbeteApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr (UserRepository userRepository, RoleRepository roleRepository) {
        return (args -> {

            saveRoleIfNotPresent(roleRepository, Role.RoleConstant.SUPER_ADMIN);
            saveRoleIfNotPresent(roleRepository, Role.RoleConstant.ADMIN);
            saveRoleIfNotPresent(roleRepository, Role.RoleConstant.USER);

        });
    }
    private void saveRoleIfNotPresent(RoleRepository roleRepository, Role.RoleConstant role) {
        if (!roleRepository.existsByName(role)) {
            roleRepository.save(new Role(role));
        }
    }
}
