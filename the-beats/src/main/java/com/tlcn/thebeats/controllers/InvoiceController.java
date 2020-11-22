package com.tlcn.thebeats.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tlcn.thebeats.models.PlacedOrder;
import com.tlcn.thebeats.payload.request.CreateInvoiceRequest;
import com.tlcn.thebeats.repository.PlacedOrderRepository;

@RestController
@CrossOrigin(origins = "*",maxAge = 3000)
@RequestMapping(value = "/api/v1/invoice/")
public class InvoiceController {
		
	@Autowired
	private PlacedOrderRepository placedOrderRepository;
	
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
	
	
	
}
