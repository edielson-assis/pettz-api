package br.com.pettz.services.impl;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.pettz.configs.FileStorageConfig;
import br.com.pettz.models.ImgUrl;
import br.com.pettz.models.Product;
import br.com.pettz.repositories.ImgUrlRepository;
import br.com.pettz.services.ImgUrlService;
import br.com.pettz.services.exceptions.FileStorageException;
import br.com.pettz.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImgUrlServiceImpl implements ImgUrlService {

    private final ImgUrlRepository repository;
    private final Path fileStorageLocation;

	public ImgUrlServiceImpl(FileStorageConfig fileStorageConfig, ImgUrlRepository repository) {
        this.repository = repository;

		Path path = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
		this.fileStorageLocation = path;
		try {
			Files.createDirectories(this.fileStorageLocation);
			log.info("File storage directory created at {}", this.fileStorageLocation);
		} catch (Exception e) {
			log.error("Could not create file storage directory", e);
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored!", e);
		}
	}
	
	public ImgUrl saveImages(MultipartFile file, Product product) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		if (filename.isBlank() || !filename.contains(".jpg")) {
			log.warn("Attempted upload of a non-JPEG file: {}", filename);
			throw new FileStorageException("Only JPEG files are accepted.");
		}

		try {
			Path targetLocation = this.fileStorageLocation.resolve(filename);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("File successfully stored: {}", filename);
			return repository.save(new ImgUrl(UUID.randomUUID(), filename, product));
		} catch (Exception e) {
			log.error("Failed to store file {}", filename, e);
			throw new FileStorageException("Could not store file " + filename + ". Please try again!", e);
		}
	}

	@Override
	public Resource loadFileAsResource(String filename) {
		try {
			Path filePath = this.fileStorageLocation.resolve(filename).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (!resource.exists()) { 
				log.error("File not found: {}" + filename);
				throw new FileNotFoundException("File not found: " + filename);
			}
			log.info("File loaded successfully: {}", filename);
			return resource;
		} catch (Exception e) {
			throw new ObjectNotFoundException("File not found: " + filename);
		}
	}

    @Override
    public void deleteAllImages(Set<ImgUrl> imgUrlsToRemove) {
        repository.deleteAll(imgUrlsToRemove);
    }
}