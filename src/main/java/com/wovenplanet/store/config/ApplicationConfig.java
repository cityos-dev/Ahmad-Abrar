package com.wovenplanet.store.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
	@Value("${storage.folder}")
    private String storageFolder;
	
	public String getStorageFolder() {
		return storageFolder;
	}

	public void setOutputFolder(String outputFile) {
		this.storageFolder = outputFile;
	}
}
