package com.tlcn.thebeats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.ImageFile;



@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
	

}
