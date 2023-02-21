package com.wovenplanet.store.repository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import com.wovenplanet.store.config.ApplicationConfig;
import com.wovenplanet.store.exception.FileNotFoundException;
import com.wovenplanet.store.exception.FileStorageException;

@Repository
public class FileSystemRepository {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final Path fileStorageLocation;
	
	@Autowired
	public FileSystemRepository(ApplicationConfig appConfig) throws IOException {
		this.fileStorageLocation = Paths.get(appConfig.getStorageFolder()).toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation);
	}
    
	/**
	 * Save file to file system
	 * @param file file to be uploaded 
	 * @param fileId id of the file to be uploaded
	 * @param createdAt time stamp
	 * @return location file storage location string
	 */
	public String save( MultipartFile file, String fileId, long createdAt) {
		Path targetLocation = null;
		log.info("save file in file system start. fileId : " + fileId);
		try {
			byte[] content = file.getBytes();
			String fileName = file.getOriginalFilename();
			targetLocation = this.fileStorageLocation.resolve(fileId + "_" + fileName);
			Files.write(targetLocation, content);
		} catch (IOException e) {
			throw new FileStorageException("Upload failed", e);
		}
		log.info("save file in file system end. fileId : " + fileId);
		return targetLocation.toAbsolutePath().toString();
	}
    
	/**
	 * find file by location
	 * @param location file location file system
	 * @return Resource file resource 
	 */
	public Resource findInFileSystem(String location) {
		
		UrlResource resource =null;
		try {
			Path filePath = Paths.get(location);
			resource = new UrlResource(filePath.toUri());
		} catch (MalformedURLException e) {
			throw new FileNotFoundException("file not found in file storage");
		}
		return resource;
	}
}
