package com.library.config;

import com.library.model.Role;
import com.library.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByRoleName("User") == null) {
                roleRepository.save(new Role(null, "User"));
            }
            if (roleRepository.findByRoleName("Admin") == null) {
                roleRepository.save(new Role(null, "Admin"));
            }
        };
    }
}
