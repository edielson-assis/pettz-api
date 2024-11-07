package br.com.pettz.mappers;

import br.com.pettz.dtos.response.ColorResponse;
import br.com.pettz.dtos.response.ColorWithIdResponse;
import br.com.pettz.models.Color;

public class ColorMapper {
    
    private ColorMapper() {}

    public static ColorResponse toDto(Color color) {
        return ColorResponse.builder().color(color.getColor()).build();
    }

    public static ColorWithIdResponse toColorWithIdDto(Color color) {
        return ColorWithIdResponse.builder()
                .idColor(color.getIdColor())
                .color(color.getColor()).build();
    }
}