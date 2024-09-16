package br.com.pettz.services;

import br.com.pettz.models.ImgUrl;
import br.com.pettz.models.Product;

public interface ImgUrlService {
    
    ImgUrl findByImgUrl(String url, Product product);
}