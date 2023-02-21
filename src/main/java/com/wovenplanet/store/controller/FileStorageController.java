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
import com.wovenplanet.store.exception.FileConflictException;
import com.wovenplanet.store.exception.FileNotFoundException;
import com.wovenplanet.store.exception.FileNotSupportedException;
import com.wovenplanet.store.model.FileData;
import com.wovenplanet.store.payload.Response;
import com.wovenplanet.store.service.FileIdGeneratorServiceImpl;
import com.wovenplanet.store.service.FileStorageServiceImpl;
import com.wovenplanet.store.service.FileValidationServiceImpl;

@RestController
@RequestMapping("v1")
class FileStorageController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FileStorageServiceImpl fileStorageService;

	@Autowired
	FileIdGeneratorServiceImpl fileIdGeneratorService;

	@Autowired
	FileValidationServiceImpl fileValidationService;

	/**
	 * API for uploading a video.
	 * 
	 * @param file The video file to upload.
	 * @return ResponseEntity  HttpStatus 201 and uploaded video location upon success.
	 */
	@PostMapping("/files")
	ResponseEntity<String> uploadFile(@RequestParam("data") MultipartFile file) {
		log.info("uploadFile start");
		if (!fileValidationService.isSupportedMedia(file))
			throw new FileNotSupportedException();
		if (fileValidationService.isNameConflict(file.getOriginalFilename()))
			throw new FileConflictException();
		String fileId = fileIdGeneratorService.generateId();
		fileStorageService.save(file, fileId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", ServletUriComponentsBuilder.fromCurrentContextPath().path(fileId).toUriString());
		log.info("uploadFile end");
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/**
	 * API for downloading a video.
	 * 
	 * @param videoId The ID of the video to download.
	 * @return A ResponseEntity with the video file as a Resource object.
	 */
	@GetMapping(value = "/files/{fileId}", produces = MediaType.TEXT_PLAIN_VALUE)
	ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
		log.info("downloadFile start for file : \" + fileId");
		if (!fileStorageService.isPresent(fileId)) {
			throw new FileNotFoundException("file not found");
		}
		ImmutablePair<FileData, Resource> returnFile = fileStorageService.find(fileId);
		log.info("downloadFile end");
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(returnFile.getKey().getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= " + returnFile.getKey().getName())
				.body(returnFile.getValue());
	}

	/**
	 * API to get list of uploaded videos.
	 * 
	 * @return A ResponseEntity with a list of uploaded video metadata.
	 */
	@RequestMapping("/files")
	public List<Response> getList() {
		log.info("getList start");
		List<Response> responseList = fileStorageService.findAll();
		log.info("getList end");
		return responseList;
	}

	/**
	 * API for deleting a video.
	 * 
	 * @param videoId The ID of the video to download.
	 * @return A ResponseEntity with HttpStatus 204 upon success.
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/files/{fileId}")
	ResponseEntity<Void> delete(@PathVariable String fileId) {
		log.info("delete start for file : " + fileId);
		if (!fileStorageService.isPresent(fileId)) {
			throw new FileNotFoundException("file not found");
		}
		fileStorageService.delete(fileId);
		log.info("delete end");
		return ResponseEntity.noContent().build();
	}
}
