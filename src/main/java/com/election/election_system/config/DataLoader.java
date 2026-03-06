/*package com.election.election_system.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.election.election_system.entity.User;
import com.election.election_system.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository repo;

    public DataLoader(UserRepository repo){
        this.repo = repo;
    }

    @Override
    public void run(String... args) {

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("1234");
        admin.setRole("ADMIN");

        repo.save(admin);
    }
}*/
package com.election.election_system.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.election.election_system.entity.User;
import com.election.election_system.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository repo;

    public DataLoader(UserRepository repo){
        this.repo = repo;
    }

    @Override
    @Transactional
    public void run(String... args) {

        // Fetch all users with username "admin"
        List<User> admins = repo.findAllByUsername("admin");

        if (admins.isEmpty()) {
            // No admin exists → create one
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("1234");
            admin.setRole("ADMIN");
            repo.save(admin);
            System.out.println("Admin user created.");
        } else if (admins.size() > 1) {
            // Multiple admins exist → keep the first, delete the rest
            @SuppressWarnings("unused")
            User firstAdmin = admins.get(0);
            for (int i = 1; i < admins.size(); i++) {
                repo.delete(admins.get(i));
            }
            System.out.println("Duplicate admin users removed. Only one admin remains.");
        } else {
            // Exactly one admin exists → do nothing
            System.out.println("Admin user already exists.");
        }
    }
}

