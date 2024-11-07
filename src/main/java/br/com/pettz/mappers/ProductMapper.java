package br.com.pettz.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.pettz.dtos.request.ProductRequest;
import br.com.pettz.dtos.request.ProductUpdateRequest;
import br.com.pettz.dtos.response.ColorResponse;
import br.com.pettz.dtos.response.ColorWithIdResponse;
import br.com.pettz.dtos.response.ImgUrlResponse;
import br.com.pettz.dtos.response.ImgUrlWithIdResponse;
import br.com.pettz.dtos.response.ProductResponse;
import br.com.pettz.dtos.response.ProductWithIdResponse;
import br.com.pettz.models.Color;
import br.com.pettz.models.ImgUrl;
import br.com.pettz.models.Product;

public class ProductMapper {
    
    private ProductMapper() {}

    public static Product toEntity(ProductRequest productRequest) {
        return Product.builder()
                .code(productRequest.code())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price()).build();
    }

    public static ProductResponse toDto(Product product) {
        return ProductResponse.builder()
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imgUrls(imgUrls(product.getImgUrls()))
                .colors(colors(product.getColors())).build();
    }

    public static ProductWithIdResponse toProductWithIdDto(Product product) {
        return ProductWithIdResponse.builder()
                .idProduct(product.getIdProduct())
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imgUrls(imgUrlsWithId(product.getImgUrls()))
                .colors(colorsWithId(product.getColors())).build();
    }

    public static void toUpdateEntity(Product product, ProductUpdateRequest productRequest) {
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
    }

    private static Set<ImgUrlResponse> imgUrls(Set<ImgUrl> imgUrls) {
        return imgUrls.stream().map(ImgUrlMapper::toDto).collect(Collectors.toSet());
    }

    private static Set<ColorResponse> colors(Set<Color> colors) {
        return colors.stream().map(ColorMapper::toDto).collect(Collectors.toSet());
    }

    private static Set<ImgUrlWithIdResponse> imgUrlsWithId(Set<ImgUrl> imgUrls) {
        return imgUrls.stream().map(ImgUrlMapper::toImgUrlWithIdDto).collect(Collectors.toSet());
    }

    private static Set<ColorWithIdResponse> colorsWithId(Set<Color> colors) {
        return colors.stream().map(ColorMapper::toColorWithIdDto).collect(Collectors.toSet());
    }
}