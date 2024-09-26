package br.com.pettz.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.pettz.dtos.request.ProductRequest;
import br.com.pettz.dtos.request.ProductUpdateRequest;
import br.com.pettz.dtos.response.ProductResponse;
import br.com.pettz.dtos.response.ProductWithIdResponse;

public interface ProductService {
    
    ProductResponse registerNewProduct(ProductRequest productRequest);

    ProductResponse findProductByName(String name);

    Page<ProductResponse> findAllProducts(Pageable pageable);

    Page<ProductWithIdResponse> findAllProductsWithId(Pageable pageable);

    ProductResponse updateProductById(UUID idProduct, ProductUpdateRequest productRequest);

    void deleteProduct(UUID idProduct);
}