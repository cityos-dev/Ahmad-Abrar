package com.wovenplanet.store.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileValidationService {

	public boolean isSupportedMedia(MultipartFile file);
	
	public boolean isNameConflict(String name);
}
