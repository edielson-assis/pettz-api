package br.com.pettz.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.pettz.models.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameAndProductIdNot(String name, UUID idCategory);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Product> findProductByName(@Param("name") String name, Pageable pageable);

    boolean existsByCode(String code);
}