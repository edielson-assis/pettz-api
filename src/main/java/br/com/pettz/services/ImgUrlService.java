package br.com.pettz.services;

import java.util.Set;

import br.com.pettz.models.ImgUrl;
import br.com.pettz.models.Product;

public interface ImgUrlService {
    
    Set<ImgUrl> findByImgUrls(Set<String> imgUrls, Product product);

    void deleteAllImages(Set<ImgUrl> imgUrlsToRemove);
}