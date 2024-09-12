package br.com.pettz.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pettz.models.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    
    boolean existsByEmail(String email);
}