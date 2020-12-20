package com.tlcn.thebeats.repository;

import java.util.List;

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
	 
	 @Query(value="SELECT count(user_song.song_id) FROM user_song WHERE user_song.user_id=:userId AND user_song.song_id=:songId",nativeQuery = true)
	 public int checkSongPayed(long userId, long songId);
	 
	 @Query(value="SELECT * FROM song, user_song WHERE user_song.user_id=:userId AND "
				+ "user_song.song_id=song.id",nativeQuery = true)
	 public Page<Song> findSongOfUser(long userId, Pageable pageable);
	 
	 @Query(value = "SELECT count(user_song.song_id) FROM user_song WHERE user_song.user_id = :userId", nativeQuery = true)
	 public int countSongOfUser(long userId);
	 @Query(value = "SELECT * FROM thebeats.song where title like %:query% or artist_name like '%'||:query||'%'", nativeQuery = true)
	 public Page<Song> search(String query,Pageable pageable);
	 
	 @Query(value = "SELECT count(id) FROM thebeats.song where title like %:query% or artist_name like '%'||:query||'%'", nativeQuery = true)
	 public int countSongByQuery(String query);
	 
	 @Query(value = "SELECT count(id) FROM song WHERE song.artist_id = :artistId", nativeQuery = true)
	 public int countSongOfArtist(int artistId);
	 
	 @Query(value="SELECT * FROM song WHERE song.artist_id=:artistId and banned=false",nativeQuery = true)
	 public Page<Song> findSongOfArtist(int artistId, Pageable pageable);
	 
}
