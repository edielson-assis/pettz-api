package br.com.pettz.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.pettz.models.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameAndIdProductNot(String name, UUID idCategory);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<Product> findByNameIgnoreCase(@Param("name") String name);

    boolean existsByCode(String code);
}