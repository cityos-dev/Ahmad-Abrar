package com.wovenplanet.store.service;

import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.wovenplanet.store.model.FileData;
import com.wovenplanet.store.payload.Response;

public interface FileStorageService {
	
	public String save(MultipartFile file);
	
	public boolean isSupportedMedia(MultipartFile file);
	
	public ImmutablePair<FileData, Resource> find(String fileId);
	
	public List<Response> findAll();
	
	public void delete(String fileId);

}
