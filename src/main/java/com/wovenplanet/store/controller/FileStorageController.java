package com.wovenplanet.store.controller;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wovenplanet.store.model.FileData;
import com.wovenplanet.store.payload.Response;
import com.wovenplanet.store.service.FileStorageServiceImpl;

@RestController
@RequestMapping("v1")
class FileStorageController {
	
    @Autowired
    FileStorageServiceImpl fileStorageService;

	@PostMapping("/files")
	ResponseEntity<String> uploadFile(@RequestParam("data") MultipartFile file) {
		if(!fileStorageService.isSupportedMedia(file))
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
		if(fileStorageService.isNameConflict(file.getOriginalFilename())) 
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		String savedFileId = fileStorageService.save(file);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location",
				ServletUriComponentsBuilder.fromCurrentContextPath().path(savedFileId).toUriString());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

    @GetMapping(value = "/files/{fileId}", produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
    	if(!fileStorageService.fileExists(fileId)) {
    		return ResponseEntity.notFound().build();
    	}
    	ImmutablePair<FileData, Resource> returnFile =  fileStorageService.find(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(returnFile.getKey().getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename= "+returnFile.getKey().getName())
                .body(returnFile.getValue());
    }
    
	@RequestMapping("/files")
	public List<Response> getAll() {
		List<Response> responseList = fileStorageService.findAll();
		return responseList;
	}
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/files/{fileId}")
    ResponseEntity<Void> delete(@PathVariable String fileId) {
    	if(!fileStorageService.fileExists(fileId)) {
    		return ResponseEntity.notFound().build();
    	}
    	fileStorageService.delete(fileId);
    	return ResponseEntity.noContent().build();
    }
}
