package br.com.pettz.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.pettz.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    UserDetails findByEmail(String email);
    
    boolean existsByEmail(String email);
}