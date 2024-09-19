package br.com.pettz.mappers;

import br.com.pettz.dtos.response.ImgUrlResponse;
import br.com.pettz.dtos.response.ImgUrlWithIdResponse;
import br.com.pettz.models.ImgUrl;

public class ImgUrlMapper {
    
    private ImgUrlMapper() {}

    public static ImgUrlResponse toDto(ImgUrl url) {
        return ImgUrlResponse.builder().imgUrl(url.getUrl()).build();
    }

    public static ImgUrlWithIdResponse toImgUrlWithIdDto(ImgUrl url) {
        return ImgUrlWithIdResponse.builder()
                .idImgUrl(url.getIdImgUrl())
                .imgUrl(url.getUrl()).build();
    }
}