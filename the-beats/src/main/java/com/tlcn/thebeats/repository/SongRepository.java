package com.tlcn.thebeats.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>  {
	 @Query(value = "SELECT * FROM Song, song_tags WHERE song_tags.tag_id = :tagId AND song_tags.song_id = Song.id ORDER BY song_tags.tag_id", nativeQuery = true)
	 public Page<Song> findAllByTagTagId(int tagId, Pageable pageable);
	 
	 @Query(value = "SELECT count(song_tags.song_id) FROM song_tags WHERE song_tags.tag_id = :tagId ORDER BY song_tags.tag_id", nativeQuery = true)
	 public int countSong(int tagId);
}
