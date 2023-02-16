package com.wovenplanet.store.repository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.wovenplanet.store.config.ApplicationConfig;
import com.wovenplanet.store.constants.Const;
import com.wovenplanet.store.exception.InvalidLocationException;
import com.wovenplanet.store.exception.FileStorageException;

@Repository
public class FileSystemRepository {
	
	private final Path fileStorageLocation;
	
	@Autowired
	public FileSystemRepository(ApplicationConfig appConfig) {
		this.fileStorageLocation = Paths.get(appConfig.getStorageFolder()).toAbsolutePath().normalize();
		try {
			 Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException(Const.MESSAGE_ERROR_STORAGE_FOLDER_CREATION, e);
		}
	}
    
	public String save( MultipartFile file, String fileId, long createdAt) {
		Path targetLocation = null;
		try {
			byte[] content = file.getBytes();
			String fileName = file.getOriginalFilename();
			targetLocation = this.fileStorageLocation.resolve(fileId + "-" + createdAt + "-" + fileName);
			Files.write(targetLocation, content);
		} catch (IOException e) {
			throw new FileStorageException(Const.MESSAGE_ERROR_FILE_UPLOADED, e);
		}
		return targetLocation.toAbsolutePath().toString();
	}
    
	public Resource findInFileSystem(String location) {
		Path filePath = Paths.get(location);
		Resource resource = null;
		try {
			resource = new UrlResource(filePath.toUri());
		} catch (MalformedURLException e) {
			throw new InvalidLocationException(Const.MESSAGE_NOT_FOUND, e);
		}
		return resource;
	}
}
