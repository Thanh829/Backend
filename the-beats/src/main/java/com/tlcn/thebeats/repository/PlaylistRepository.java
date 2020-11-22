package com.tlcn.thebeats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.Playlist;



@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer>{

	public List<Playlist> findAllByUserId(long userId);

	
}
