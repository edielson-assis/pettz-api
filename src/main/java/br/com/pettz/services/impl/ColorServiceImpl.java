package br.com.pettz.services.impl;

import org.springframework.stereotype.Service;

import br.com.pettz.models.Color;
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
    public synchronized Color findByColor(String name) {
        log.info("Searching for color with name: {}", name);
        return repository.findByNameIgnoreCase(name).orElseGet(() -> {
            log.info("Registering a new Color: {}", name);
            return repository.save(new Color(null, name));
        });
    }
}