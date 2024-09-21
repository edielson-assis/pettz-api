package br.com.pettz.services.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    public synchronized Set<ImgUrl> findByImgUrls(Set<String> imgUrls, Product product) {
        Set<ImgUrl> processedImgUrls = new HashSet<>();

        for (String url : imgUrls) {
            log.info("Searching for image URL: {}", url);
            ImgUrl existingImgUrl = repository.findByUrlIgnoreCase(url).orElseGet(() -> {
                log.info("Registering a new image URL: {}", url);
                return repository.save(new ImgUrl(UUID.randomUUID(), url, product));
            });
            processedImgUrls.add(existingImgUrl);
        }
        return processedImgUrls;
    }

    @Override
    public void deleteAllImages(Set<ImgUrl> imgUrlsToRemove) {
        repository.deleteAll(imgUrlsToRemove);
    }
}