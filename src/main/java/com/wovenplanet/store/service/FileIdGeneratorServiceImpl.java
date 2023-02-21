package com.wovenplanet.store.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wovenplanet.store.exception.FileStorageException;

@Service
public class FileIdGeneratorServiceImpl implements FileIdGeneratorService {

	/**
     * generate unique file id for storage.
     */
	@Override
	public String generateId() {
		return RandomStringUtils.randomAlphanumeric(8);
	}
	
	/**
	 * Generate unique id by hashing file content.
	 * function not used. Implemented as an idea of comparing file data for
	 * duplication. Would like to discuss further in next technical interview round
	 */
	public String generateId(MultipartFile file) {
        byte[] hash = null;
		try {
			hash = calculateFileHash(file.getInputStream());
		} catch (NoSuchAlgorithmException | IOException e) {
			throw new FileStorageException("File id generation failed", e);
		}
        return bytesToHex(hash);
    }
    
    private byte[] calculateFileHash(InputStream inputStream) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            md.update(buffer, 0, bytesRead);
        }
        return md.digest();
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
