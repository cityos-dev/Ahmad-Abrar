package com.wovenplanet.store.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
	@JsonProperty("fileid")
	private String fileId;
	private String name;
	private long size;
	@JsonProperty("created_at")
    private String createdAt;
    

    public Response(String fileId, String fileName, long size, String createdAt) {
    	this.fileId = fileId;
    	this.name = fileName;
    	this.size = size;
        this.createdAt = createdAt;
    }

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
