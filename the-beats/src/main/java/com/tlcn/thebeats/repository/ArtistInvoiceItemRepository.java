package com.tlcn.thebeats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.ArtistInvoiceItem;

@Repository
public interface ArtistInvoiceItemRepository extends JpaRepository<ArtistInvoiceItem, Long>{
	
	@Query(value = "select * from artist_invoice_item where invoice_id=:id",nativeQuery = true)
		public List<ArtistInvoiceItem> getInvoiceById(int id);

}
