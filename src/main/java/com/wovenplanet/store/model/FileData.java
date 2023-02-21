package com.wovenplanet.store.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wovenplanet.store.constants.Const;
import com.wovenplanet.store.constants.Const.STATUS;

@Entity
@Table(name = "FILE")
public class FileData {

    @Id
    String fileId;

    String name;

    String location;
    
    String contentType;
    
    long size;
    
    long createdAt;
    
    STATUS status;

	public FileData() {
    }

    public FileData(String fileId, String name, String location, String contentType, long size, long createdAt) {
    	this.fileId = fileId;
        this.name = name;
        this.location = location;
        this.contentType = contentType;
        this.size = size;
        this.status = Const.STATUS.PRESENT;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Const.STATUS getStatus() {
		return status;
	}

	public void setStatus(Const.STATUS status) {
		this.status = status;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
