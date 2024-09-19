package br.com.pettz.services;

import java.util.Set;

import br.com.pettz.models.Color;
import br.com.pettz.models.Product;

public interface ColorService {

    Set<Color> findByColors(Set<String> colors, Product product);
}