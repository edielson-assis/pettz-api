package br.com.pettz.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pettz.models.Color;

public interface ColorRepository extends JpaRepository<Color, UUID> {

    Optional<Color> findByColorIgnoreCase(String name);
}