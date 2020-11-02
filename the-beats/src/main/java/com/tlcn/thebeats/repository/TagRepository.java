package com.tlcn.thebeats.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>{
	Optional<Tag> findByTitle(String title);
}
