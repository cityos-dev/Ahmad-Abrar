package com.wovenplanet.store.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wovenplanet.store.config.ApplicationConfig;
import com.wovenplanet.store.constants.Const;
import com.wovenplanet.store.repository.FileDbRepository;

@Service
public class FileValidationServiceImpl implements FileValidationService{

	@Autowired
    FileDbRepository fileDbRepository;
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	/**
	 * Check if input media type is supported.
	 * @param file The video file to upload.
	 * @return boolean if input media is supported or not
	 */
	@Override
	public boolean isSupportedMedia(MultipartFile file) {
		for (String contentType : applicationConfig.getContents().values()) {
			if (file.getContentType().equals(contentType))
				return true;
			if (contentType.contains(FilenameUtils.getExtension(file.getOriginalFilename())))
				return true;
		}
		return false;
	}
	
	/**
	 * Check if file exists with input file name.
	 * @param filename name of the file to be uploaded 
	 * @return boolean if input media is supported or not
	 */
	@Override
	public boolean isNameConflict(String fileName) {
		if (fileDbRepository.existsByNameAndStatus(fileName, Const.STATUS.PRESENT))
			return true;
		return false;
	}
}
