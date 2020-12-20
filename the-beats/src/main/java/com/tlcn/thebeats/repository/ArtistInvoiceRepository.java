package com.tlcn.thebeats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.ArtistInvoice;

@Repository
public interface ArtistInvoiceRepository extends JpaRepository<ArtistInvoice, Integer> {

	
	public List<ArtistInvoice> findAllByArtistId(int artistId);
	
	@Query(value = "select * from thebeats.artist_invoice where artist_id=:artistId",nativeQuery = true)
	public List<ArtistInvoice> getAllByArtistId(int artistId);
}
