package br.com.pettz.services;

import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;

import br.com.pettz.dtos.request.ProductRequest;
import br.com.pettz.dtos.request.ProductUpdateRequest;
import br.com.pettz.dtos.response.ProductResponse;
import br.com.pettz.dtos.response.ProductWithIdResponse;

public interface ProductService {
    
    ProductResponse registerNewProduct(ProductRequest productRequest);

    Page<ProductResponse> findProductByName(String name, Integer page, Integer size, String direction);

    Page<ProductResponse> findAllProducts(Integer page, Integer size, String direction);

    Page<ProductWithIdResponse> findAllProductsWithId(Integer page, Integer size, String direction);

    Resource getImages(String filename);

    ProductResponse updateProductById(UUID idProduct, ProductUpdateRequest productRequest);

    void deleteProduct(UUID idProduct);
}