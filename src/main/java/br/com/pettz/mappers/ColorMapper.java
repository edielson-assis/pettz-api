package br.com.pettz.mappers;

import br.com.pettz.dtos.request.ColorRequest;
import br.com.pettz.models.Color;

public class ColorMapper {
    
    private ColorMapper() {}

    public static Color toEntity(ColorRequest color) {
        return Color.builder().color(color.name()).build();
    }
}