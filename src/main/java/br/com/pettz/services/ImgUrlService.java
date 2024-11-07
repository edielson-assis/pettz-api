package br.com.pettz.services;

import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import br.com.pettz.models.ImgUrl;
import br.com.pettz.models.Product;

public interface ImgUrlService {
    
    ImgUrl saveImages(MultipartFile imgUrls, Product product);

    Resource loadFileAsResource(String filename);

    void deleteAllImages(Set<ImgUrl> imgUrlsToRemove);
}