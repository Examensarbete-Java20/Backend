package com.example.examensarbete;

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
            User user = new User();

            user.addRoleToUser("SUPER_ADMIN", roleRepository);
            user.addRoleToUser("ADMIN", roleRepository);
            user.addRoleToUser("USER", roleRepository);

            User savedUser = userRepository.save(user);
            log.info("User added to the application {}", savedUser);
        });
    }
}
