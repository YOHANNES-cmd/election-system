package com.election.election_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.election.election_system.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
 boolean existsByUsername(String username); 
 List<User> findAllByUsername(String username);
}