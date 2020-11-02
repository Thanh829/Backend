package com.tlcn.thebeats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>  {

}
