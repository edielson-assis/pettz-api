package br.com.pettz.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.pettz.dtos.response.ImgUrlResponse;
import br.com.pettz.dtos.response.ImgUrlWithIdResponse;
import br.com.pettz.models.ImgUrl;

public class ImgUrlMapper {

    private static final String IMAGE_URL = "http://localhost:8080/api/v1/products/images/";
    
    private ImgUrlMapper() {}

    public static ImgUrlResponse toDto(ImgUrl url) {
        return ImgUrlResponse.builder().imgUrl(IMAGE_URL.concat(url.getUrl())).build();
    }

     public static Set<ImgUrlResponse> imgUrls(Set<ImgUrl> imgUrls) {
        return imgUrls.stream().map(ImgUrlMapper::toDto).collect(Collectors.toSet());
    }

    public static ImgUrlWithIdResponse toImgUrlWithIdDto(ImgUrl url) {
        return ImgUrlWithIdResponse.builder()
                .idImgUrl(url.getImgUrlId())
                .imgUrl(IMAGE_URL.concat(url.getUrl())).build();
    }
}