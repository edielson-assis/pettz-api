package br.com.pettz.services;

import br.com.pettz.models.Color;

public interface ColorService {

    Color findByColor(String name);
}