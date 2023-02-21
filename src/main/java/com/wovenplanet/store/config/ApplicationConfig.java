package com.wovenplanet.store.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class ApplicationConfig {
	@Value("${storage.folder}")
    private String storageFolder;
	
	private Map<String, String> contents = new HashMap<>();
	
	public String getStorageFolder() {
		return storageFolder;
	}

	public void setOutputFolder(String outputFile) {
		this.storageFolder = outputFile;
	}
	
	public Map<String, String> getContents() {
		return contents;
	}

	public void setContents(Map<String, String> contents) {
		this.contents = contents;
	}
}
