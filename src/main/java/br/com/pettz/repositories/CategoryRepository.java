package br.com.pettz.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pettz.models.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameAndIdCategoryNot(String name, UUID idCategory);

    Optional<Category> findByNameIgnoreCase(String name);
}