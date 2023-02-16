package com.wovenplanet.store.payload;

public class Response {
	private String fileId;
	private String fileName;
	private long size;
    private String createdAt;
    

    public Response(String fileId, String fileName, long size, String createdAt) {
    	this.fileId = fileId;
    	this.fileName = fileName;
    	this.size = size;
        this.createdAt = createdAt;
    }

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
