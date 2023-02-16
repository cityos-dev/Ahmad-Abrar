package com.wovenplanet.store.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wovenplanet.store.model.FileData;

@Repository
public interface FileDbRepository extends JpaRepository<FileData, String> {

	List<FileData> findAllByStatus(int status);
	
	boolean existsByFileIdAndStatus(String fileId, int status);
	
	boolean existsByNameAndStatus(String name, int status);

	FileData findByFileIdAndStatus(String fileId, int status);
}
