package com.wovenplanet.store.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wovenplanet.store.constants.Const;
import com.wovenplanet.store.model.FileData;

@Repository
public interface FileDbRepository extends JpaRepository<FileData, Const.STATUS> {

	List<FileData> findAllByStatus(Const.STATUS status);
	
	boolean existsByFileIdAndStatus(String fileId, Const.STATUS status);
	
	boolean existsByNameAndStatus(String name, Const.STATUS status);

	FileData findByFileIdAndStatus(String fileId, Const.STATUS status);
}
