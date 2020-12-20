package com.tlcn.thebeats.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.Artist;
import com.tlcn.thebeats.models.Song;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

	public Artist findByUserId(long userId);
	
	
	@Query(value = "SELECT artist.id,artist.user_id, paypal_account,  IF(sum(bought_song.price) IS NULL, 0, sum(bought_song.price))\r\n" + 
			"	as payslip,user_id,avatar,cover_image_id,artist_name,is_active \r\n" + 
			"		FROM thebeats.artist, bought_song where bought_song.artist_id=thebeats.artist.id and bought_song.is_pay_for_arist=false group by artist_id\r\n" + 
			"		union all\r\n" + 
			"            \r\n" + 
			"            \r\n" + 
			"            \r\n" + 
			"			SELECT tb1.id,tb1.user_id, tb1.paypal_account,tb1.payslip,tb1.user_id,tb1.avatar,\r\n" + 
			"				tb1.cover_image_id,tb1.artist_name,tb1.is_active \r\n" + 
			"                FROM (SELECT artist.id, paypal_account,payslip,user_id,avatar,\r\n" + 
			"				cover_image_id,artist_name,is_active FROM thebeats.artist) as tb1,(SELECT artist.id, paypal_account,  IF(sum(bought_song.price) IS NULL, 0, sum(bought_song.price))\r\n" + 
			"	as payslip,user_id,avatar,cover_image_id,artist_name,is_active \r\n" + 
			"		FROM thebeats.artist, bought_song where bought_song.artist_id=thebeats.artist.id and bought_song.is_pay_for_arist=false group by artist_id\r\n" + 
			") as tb2\r\n" + 
			"				where tb1.id<>tb2.id",nativeQuery = true)
	public List<Artist> findAllArtist();
	
	@Query(value = "select * from artist where artist.is_active=true", nativeQuery = true)
	public Page<Artist> findActiveArtist(Pageable pageable);
	@Query(value = "select count(id) from artist where artist.is_active=true", nativeQuery = true)
	public int countActiveArtist();
	
	
}
