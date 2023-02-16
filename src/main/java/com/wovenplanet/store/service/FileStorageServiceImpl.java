package com.wovenplanet.store.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.wovenplanet.store.constants.Const;
import com.wovenplanet.store.model.FileData;
import com.wovenplanet.store.payload.Response;
import com.wovenplanet.store.repository.FileDbRepository;
import com.wovenplanet.store.repository.FileSystemRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    @Autowired
    FileSystemRepository fileSystemRepository;
    @Autowired
    FileDbRepository fileDbRepository;

	@Override
	public String save(MultipartFile file) {
		String fileId = RandomStringUtils.randomAlphanumeric(8);
		long createdAt = Instant.now().getEpochSecond();
		String location = fileSystemRepository.save(file, fileId, createdAt);
		fileDbRepository.save(new FileData(fileId, file.getOriginalFilename(), location, file.getContentType(),
				file.getSize(), createdAt));
		return fileId;
	}
    
	public boolean isSupportedMedia(MultipartFile file) {
		if (file.getContentType().equalsIgnoreCase(Const.SUPPORTED_MEDIA_TYPE + Const.SUPPORTED_MEDIA_EXT_01)
				|| file.getContentType().equalsIgnoreCase(Const.SUPPORTED_MEDIA_TYPE + Const.SUPPORTED_MEDIA_EXT_02))
			return true;
		if (FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase(Const.SUPPORTED_MEDIA_EXT_01)
				|| FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase(Const.SUPPORTED_MEDIA_EXT_02))
			return true;
		return false;
	}
	
	public boolean isNameConflict(String fileName) {
		if (fileDbRepository.existsByNameAndStatus(fileName, Const.STATUS_PRESENT))
			return true;
		return false;
	}

	@Override
	public ImmutablePair<FileData, Resource> find(String fileId) {
		FileData file = fileDbRepository.findByFileIdAndStatus(fileId, Const.STATUS_PRESENT);
		Resource resource = fileSystemRepository.findInFileSystem(file.getLocation());
		return new ImmutablePair<FileData, Resource>(file, resource);
	}

	@Override
	public List<Response> findAll() {
		List<FileData> files = fileDbRepository.findAllByStatus(Const.STATUS_PRESENT);
		Collections.sort(files, new Comparator<FileData>() {
	        public int compare(FileData f1, FileData f2) {
	            if(f2.getCreatedAt()>f1.getCreatedAt())
	            	return 1;
	            return -1;		
	        }
	    });
		List<Response> responseList = new ArrayList<>();
		for (FileData file : files) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss'Z'")
					.withZone(ZoneOffset.UTC);
			String utcTimestamp = formatter.format(Instant.ofEpochSecond(file.getCreatedAt()));
			responseList.add(new Response(file.getFileId(), file.getName(), file.getSize(), utcTimestamp));
		}
		return responseList;
	}

	/**
     * soft delete only. Garbage Collector implementation required for hard delete
     */
	@Override
	public void delete(String fileId) {
		FileData file = fileDbRepository.findByFileIdAndStatus(fileId, Const.STATUS_PRESENT);
		file.setStatus(Const.STATUS_DELETED);
		fileDbRepository.save(file);
	}
	
	public boolean fileExists(String fileId) {
		return fileDbRepository.existsByFileIdAndStatus(fileId, Const.STATUS_PRESENT);
	}
}
