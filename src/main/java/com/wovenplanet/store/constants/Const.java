package com.wovenplanet.store.constants;

public class Const {
	public static final Integer STATUS_PRESENT = 1;
	public static final Integer STATUS_DELETED = 0;
	
	public static final String SUPPORTED_MEDIA_TYPE = "video/";
	public static final String SUPPORTED_MEDIA_EXT_01 = "mp4";
	public static final String SUPPORTED_MEDIA_EXT_02 = "mpeg";
	
	public static final String MESSAGE_NOT_FOUND = "File does not exist";
	public static final String MESSAGE_UNSUPPORTED_FILE = "Requested file cannot be uploaded. Supported files are : mp4 or mpeg";
	public static final String MESSAGE_ERROR_FILE_UPLOADED = "File upload failed. Please try again.";
	public static final String MESSAGE_ERROR_STORAGE_FOLDER_CREATION = "Could not create the directory where the uploaded files will be stored";
}
