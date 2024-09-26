package br.com.pettz.services.impl;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.request.ColorRequest;
import br.com.pettz.dtos.request.ImgUrlRequest;
import br.com.pettz.dtos.request.ProductRequest;
import br.com.pettz.dtos.request.ProductUpdateRequest;
import br.com.pettz.dtos.response.ProductResponse;
import br.com.pettz.dtos.response.ProductWithIdResponse;
import br.com.pettz.mappers.ProductMapper;
import br.com.pettz.models.Category;
import br.com.pettz.models.Color;
import br.com.pettz.models.ImgUrl;
import br.com.pettz.models.Product;
import br.com.pettz.repositories.ProductRepository;
import br.com.pettz.services.CategoryService;
import br.com.pettz.services.ColorService;
import br.com.pettz.services.ImgUrlService;
import br.com.pettz.services.ProductService;
import br.com.pettz.services.exceptions.ObjectNotFoundException;
import br.com.pettz.services.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ImgUrlService imgUrlService;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private static final String PRODUCT_ALREADY_EXISTS = "Product already exists";
    private static final String PRODUCT_NOT_FOUND = "Product not found";

    @Override
    public ProductResponse registerNewProduct(ProductRequest productRequest) {
        Product product = ProductMapper.toEntity(productRequest);
        validateProductExists(product);
        validateCodeExists(product);
        product = repository.save(product);
        product.getImgUrls().addAll(imgUrls(productRequest, product));
        product.getColors().addAll(colors(productRequest, product));
        product.getCategories().addAll(categories(productRequest, product));
        log.info("Registering a new product: {}", product.getName());
        return ProductMapper.toDto(repository.save(product));
    }

    @Override
    public ProductResponse findProductByName(String product) {
        log.info("Searching for product with name: {}", product);
        return repository.findByNameIgnoreCase(product).map(ProductMapper::toDto).orElseThrow(() -> {
            log.error(PRODUCT_NOT_FOUND.concat(": {}"), product);
            return new ObjectNotFoundException(PRODUCT_NOT_FOUND);
        });
    }

    @Override
    public Page<ProductResponse> findAllProducts(Pageable pageable) {
        log.info("Searching all products");
        return repository.findAll(pageable).map(ProductMapper::toDto);
    }

    @Override
    public Page<ProductWithIdResponse> findAllProductsWithId(Pageable pageable) {
        log.info("Searching all products");
        return repository.findAll(pageable).map(ProductMapper::toProductWithIdDto);
    }

    @Override
    public ProductResponse updateProductById(UUID idProduct, ProductUpdateRequest productRequest) {
        Product product = findProductById(idProduct);
        ProductMapper.toUpdateEntity(product, productRequest);
        validateProductNotExists(product);
        log.info("Updating product with name: {}", product.getName());
        return ProductMapper.toDto(repository.save(product));
    }

    @Override
    public void deleteProduct(UUID idProduct) {
        Product product = findProductById(idProduct);
        removeImages(product);
        removeColors(product);
        removeCategories(product);
        repository.delete(product);
    }

    private synchronized void validateProductExists(Product product) {
        boolean exists = repository.existsByNameIgnoreCase(product.getName());
        if (exists) {
            log.error(PRODUCT_ALREADY_EXISTS.concat(": {}"), product.getName());
            throw new ValidationException(PRODUCT_ALREADY_EXISTS);
        }
    }

    private synchronized void validateProductNotExists(Product product) {
        boolean exists = repository.existsByNameAndIdProductNot(product.getName(), product.getIdProduct());
        if (exists) {
            log.error(PRODUCT_ALREADY_EXISTS.concat(": {}"), product.getName());
            throw new ValidationException(PRODUCT_ALREADY_EXISTS);
        }
    }

    private synchronized void validateCodeExists(Product product) {
        boolean exists = repository.existsByCode(product.getCode().toUpperCase());
        if (exists) {
            log.error("Code already exists: {}", product.getCode());
            throw new ValidationException("Code already exists: " + product.getCode());
        }
    }

    private Product findProductById(UUID productId) {
        log.info("Searching for product with ID: {}", productId);
        return repository.findById(productId).orElseThrow(() -> {
            log.error(PRODUCT_NOT_FOUND.concat(": {}"), productId);
            return new ObjectNotFoundException(PRODUCT_NOT_FOUND);
        });
    }

    private Set<ImgUrl> imgUrls(ProductRequest productRequest, Product product) {
        var imgUrls = productRequest.imgUrls().stream().map(ImgUrlRequest::imgUrl).collect(Collectors.toSet());
        return imgUrlService.findByImgUrls(imgUrls, product);
    }

    private Set<Color> colors(ProductRequest productRequest, Product product) {
        var colors = productRequest.colors().stream().map(ColorRequest::name).collect(Collectors.toSet());
        return colorService.findByColors(colors, product);
    }

    private Set<Category> categories(ProductRequest productRequest, Product product) {
        var categories = productRequest.categories().stream().map(CategoryRequest::name).collect(Collectors.toSet());
        return categoryService.findByCategories(categories, product);
    }

    private void removeImages(Product product) {
        var imageUrls = product.getImgUrls().stream().map(ImgUrl::getUrl).collect(Collectors.toSet());
        var imgUrlsToRemove = product.getImgUrls().stream().filter(imgUrl -> imageUrls.contains(imgUrl.getUrl())).collect(Collectors.toSet());

        if (!imgUrlsToRemove.isEmpty()) {
            log.info("Removing {} images from product: {}", imgUrlsToRemove.size(), product.getName());
            product.getImgUrls().removeAll(imgUrlsToRemove);
            imgUrlService.deleteAllImages(imgUrlsToRemove);
        }
    }

    private void removeColors(Product product) {
        var colors = product.getColors().stream().map(Color::getColor).collect(Collectors.toSet());
        var colorsToRemove = product.getColors().stream().filter(color -> colors.contains(color.getColor())).collect(Collectors.toSet());

        if (!colorsToRemove.isEmpty()) {
            log.info("Removing associations between product: {} and {} colors", product.getName(), colorsToRemove.size());
            product.getColors().removeAll(colorsToRemove);
        }
    }

    private void removeCategories(Product product) {
        var categories = product.getCategories();
        
        if (!categories.isEmpty()) {
            log.info("Removing associations between product: {} and {} categories", product.getName(), categories.size());
            for (Category category : categories) {
                category.getProducts().remove(product); 
            }
            product.getCategories().clear(); 
            //repository.save(product);
        }
    }
}