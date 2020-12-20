package com.tlcn.thebeats.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.BoughtSong;
import com.tlcn.thebeats.payload.response.BoughSongResponse;

@Repository
public interface BoughtSongRepository extends JpaRepository<BoughtSong, Long> {
	
	@Query(value = "SELECT bought_song.artist_id as artistId, song_id as songId ,count(song_id)as quaranty,song.title, song.price, sum(song.price) as total \r\n" + 
			"  FROM song,thebeats.bought_song where song.id=song_id and bought_song.artist_id=:artistId "
			+ "and bought_song.is_pay_for_arist=false group by song_id ", nativeQuery = true)
	List<BoughSongResponse> getBoughtSongArtist(long artistId);
	
	@Modifying
    @Transactional
	@Query(value = "update bought_song set is_pay_for_arist=true where is_pay_for_arist=false and artist_id=:artistId", nativeQuery = true)
	public void upadteBoughtSong(int artistId);
	
	
	@Modifying
    @Transactional
	@Query(value = "update artist set artist.payslip=0 where artist.id=:artistId", nativeQuery = true)
	public void updatePayslip(int artistId);
	
//	@Query("SELECT new com.tlcn.thebeats.payload.response.BoughSongResponse(bought_song.artist_id, song_id ,count(song_id),song.title, song.price, sum(song.price)) " + 
//			"  FROM song,thebeats.bought_song where song.id=song_id and bought_song.artist_id=:artistId "
//			+ "and bought_song.is_pay_for_arist=false group by song_id ")
//	List<BoughSongResponse> getBoughtSongArtist(long artistId);

}
