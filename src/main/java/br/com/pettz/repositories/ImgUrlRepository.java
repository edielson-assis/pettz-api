package br.com.pettz.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pettz.models.ImgUrl;

public interface ImgUrlRepository extends JpaRepository<ImgUrl, UUID> {
    
    Optional<ImgUrl> findByUrlIgnoreCase(String url);
}