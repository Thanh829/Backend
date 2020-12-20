package com.tlcn.thebeats.controllers;

import java.util.List;
import java.util.Optional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tlcn.thebeats.models.ArtistInvoice;
import com.tlcn.thebeats.models.ArtistInvoiceItem;
import com.tlcn.thebeats.models.PlacedOrder;
import com.tlcn.thebeats.payload.request.CreateArtistInvoiceRequest;
import com.tlcn.thebeats.payload.request.CreateInvoiceRequest;
import com.tlcn.thebeats.payload.response.ArtistInvoiceResponse;
import com.tlcn.thebeats.repository.ArtistInvoiceItemRepository;
import com.tlcn.thebeats.repository.ArtistInvoiceRepository;
import com.tlcn.thebeats.repository.PlacedOrderRepository;

@RestController
@CrossOrigin(origins = "*",maxAge = 3000)
@RequestMapping(value = "/api/v1/invoice/")
public class InvoiceController {
		
	@Autowired
	private PlacedOrderRepository placedOrderRepository;
	
	@Autowired
	private ArtistInvoiceItemRepository artistInvoiceItemRepository;
	
	@Autowired
	private ArtistInvoiceRepository artistInvoiceRepository;
	
	@GetMapping(value = "/personal")
	public PlacedOrder getInvoice(@RequestParam int invoiceId)
	{
		Optional<PlacedOrder> invoice= placedOrderRepository.findById(invoiceId);
		if(!invoice.isPresent())
			throw new RuntimeException("Invoice not found");
		return placedOrderRepository.findById(invoiceId).get();
	}
	
	@GetMapping(value = "/personal/all")
	public List<PlacedOrder> getAllPersonal(@RequestParam int userId)
	{
		return placedOrderRepository.findAllByUserId(userId);
	}
	
	@GetMapping(value = "/all")
	public List<PlacedOrder> getAll()
	{
		return placedOrderRepository.findAll();
	}
	
	@PostMapping(value = "/create")
	public PlacedOrder createInvoice( @RequestBody CreateInvoiceRequest createInvoiceRequest)
	{
		PlacedOrder invoice= new PlacedOrder(createInvoiceRequest.getUserId(),createInvoiceRequest.getUserName(),
				createInvoiceRequest.getOrderDate(),createInvoiceRequest.getPayeeEmail(),
				createInvoiceRequest.getPaymentId(),createInvoiceRequest.getTotalCost());
		return placedOrderRepository.save(invoice);
	}
	
	@PostMapping(value = "/create-artist-invoice")
	public ArtistInvoice createArtistInvoice(@RequestBody CreateArtistInvoiceRequest artistInvoiceRequest )
	{
		ArtistInvoice artistInvoice= new ArtistInvoice(artistInvoiceRequest.getArtistId(),
				artistInvoiceRequest.getSubtotal(), artistInvoiceRequest.getTotal(), 
				artistInvoiceRequest.getPayer(), artistInvoiceRequest.getPayee(), LocalDate.now().toString());
		artistInvoiceRepository.saveAndFlush(artistInvoice);
		List<com.tlcn.thebeats.payload.request.ArtistInvoiceItem> items = artistInvoiceRequest.getItems();
		for(int i=0; i< items.size();i++)
		{
			ArtistInvoiceItem artistInvoiceItem= new ArtistInvoiceItem(items.get(i).getPrice(),
					items.get(i).getQuaranty(), items.get(i).getTotal(),
					items.get(i).getTitle(), artistInvoice);
			artistInvoiceItemRepository.save(artistInvoiceItem);
		}
		
		return artistInvoice;
	}
	
	@GetMapping(value = "/artist/get-artist-invoices")
	public List<ArtistInvoice> getArtistInvoices(@RequestParam int artistId)
	{
		return artistInvoiceRepository.findAllByArtistId(artistId);
	}
	
	@GetMapping("/artist")
	public ArtistInvoiceResponse getDetailsInvoice(@RequestParam int id)
	{
		ArtistInvoice artistInvoice = artistInvoiceRepository.findById(id).orElseThrow(()->new RuntimeException("Invoice not found"));
		
		List<ArtistInvoiceItem> items = artistInvoiceItemRepository.getInvoiceById(id);
		
		ArtistInvoiceResponse response = new ArtistInvoiceResponse(id, items, artistInvoice.getSubtotal(), artistInvoice.getTotal(), artistInvoice.getPayer(), artistInvoice.getPayee(), artistInvoice.getDate());
		
		return response;
	}
	
	
}
