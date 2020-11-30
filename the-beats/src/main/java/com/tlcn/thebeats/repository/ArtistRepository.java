package com.tlcn.thebeats.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

	
}
