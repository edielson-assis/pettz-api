package br.com.pettz.services.impl;

import org.springframework.stereotype.Service;

import br.com.pettz.models.ImgUrl;
import br.com.pettz.models.Product;
import br.com.pettz.repositories.ImgUrlRepository;
import br.com.pettz.services.ImgUrlService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ImgUrlServiceImpl implements ImgUrlService {

    private final ImgUrlRepository repository;

    @Override
    public synchronized ImgUrl findByImgUrl(String url, Product product) {
        log.info("Searching for color with name: {}", url);
        return repository.findByNameIgnoreCase(url).orElseGet(() -> {
            log.info("Registering a new Color: {}", url);
            return repository.save(new ImgUrl(null, url, product));
        });
    }    
}