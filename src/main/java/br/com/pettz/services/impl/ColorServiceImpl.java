package br.com.pettz.services.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.pettz.models.Color;
import br.com.pettz.models.Product;
import br.com.pettz.repositories.ColorRepository;
import br.com.pettz.services.ColorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository repository;

    @Override
    public synchronized Set<Color> findByColors(Set<String> colors, Product product) {
        Set<Color> processedColors = new HashSet<>();
    
        for (String colorName : colors) {
            log.info("Searching for color: {}", colorName);
            Color existingColor = repository.findByColorIgnoreCase(colorName).orElseGet(() -> {
                log.info("Registering a new color: {}", colorName);
                return repository.save(new Color(UUID.randomUUID(), colorName));
            });
            processedColors.add(existingColor);
        }
        return processedColors;
    }
}