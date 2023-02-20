package com.wovenplanet.store.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.wovenplanet.store.constants.Const;
import com.wovenplanet.store.repository.FileDbRepository;

@Service
public class FileValidationServiceImpl implements FileValidationService{

	@Autowired
    FileDbRepository fileDbRepository;
	
	@Override
	public boolean isSupportedMedia(MultipartFile file) {
		if (file.getContentType().equalsIgnoreCase(Const.SUPPORTED_MEDIA_TYPE + Const.SUPPORTED_MEDIA_EXT_01)
				|| file.getContentType().equalsIgnoreCase(Const.SUPPORTED_MEDIA_TYPE + Const.SUPPORTED_MEDIA_EXT_02))
			return true;
		if (FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase(Const.SUPPORTED_MEDIA_EXT_01)
				|| FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase(Const.SUPPORTED_MEDIA_EXT_02))
			return true;
		return false;
	}
	
	@Override
	public boolean isNameConflict(String fileName) {
		if (fileDbRepository.existsByNameAndStatus(fileName, Const.STATUS_PRESENT))
			return true;
		return false;
	}
}
