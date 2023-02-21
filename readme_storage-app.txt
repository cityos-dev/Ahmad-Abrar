Video Storage Utility


Overview
This is a Spring Boot utility that allows users to upload and download video files. 
The utility uses Spring Boot+AOP and H2 database RDB to store video files and their metadata respectively.


Features
Please access below url for API specifications available with this utility
	http://localhost:8080/swagger-ui.html 

following features are available
Upload video
Download video
List all stored videos
Delete video


Requirements
-Java 8 or higher
-H2Base
-Apache Maven


Getting Started
-Clone repository
-Set up data base properties in application.properties
	Default H2 creates and embedded data base. 
	If you want to use db seperate instance start db and configure required setting
-Build the project using Maven
	mvn clean install
-Run project
	java -jar target/storage-app-1.0.0.jar
-access application at
	Access the application at http://localhost:8080/v1


Future improvements
-Hard Delete
	Implement a process which runs on fixed interval and cleans up non referenced files stored in file storage.
-Conflict by file data
	Conflict by name is implemented. But this algorithm can be further modified to also check for file data conflicts.
	We can do this by creating hash from file data as fileId. An implementation fo similar function is done for reference.
-Data Storage design
	Create composite key using fileId and name for faster quering
	using S3 bucket for blob storage
	Use redis cache for Caching metadata.