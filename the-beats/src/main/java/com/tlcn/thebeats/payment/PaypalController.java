package com.tlcn.thebeats.payment;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import com.tlcn.thebeats.models.BoughtSong;
import com.tlcn.thebeats.models.CartItem;
import com.tlcn.thebeats.models.Tag;
import com.tlcn.thebeats.models.User;
import com.tlcn.thebeats.payload.response.ReviewResponse;
import com.tlcn.thebeats.payload.response.URLResponse;
import com.tlcn.thebeats.repository.BoughtSongRepository;
import com.tlcn.thebeats.repository.CartItemRepository;
import com.tlcn.thebeats.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaypalController {

	@Autowired
	PaypalService service;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BoughtSongRepository boughtSongRepository;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/review")
	public ReviewResponse getReviewPayment(@RequestParam("paymentId") String paymentId) {
		Payment payment;
		try {
			payment = service.getPaymentDetails(paymentId);
			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);
			ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();

			return new ReviewResponse(payerInfo, transaction, shippingAddress);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new RuntimeException("Not found PaymentID");

	}
	
	@PostMapping("/pay")
	public URLResponse payment(@RequestBody Order order) {
		try {
			Payment payment = service.createPayment(order.getEmailPaypal(), order.getItems(), order.getTotal(), order.getCurrency(),
					order.getMethod(), order.getIntent(), order.getDescription(), "http://localhost:4200/",
					order.getSuccessUrl());
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					return new URLResponse(link.getHref());
				}
			}

		} catch (PayPalRESTException e) {

			e.printStackTrace();
		}
		return new URLResponse("/");
	}
	

	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "cancel";
	}

	@GetMapping(value = SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
			@RequestParam int userId) {
		try {
			Payment payment = service.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				List<CartItem> items = cartItemRepository.findByUserId(userId);

				User user = userRepository.findById((long) userId)
						.orElseThrow(() -> new RuntimeException("User not found to add song"));
				items.stream().forEach(item -> {
					userRepository.saveSong(userId, item.getSongId());
					boughtSongRepository.save(new BoughtSong(userId, item.getArtistId(), item.getSongId(),
							new Date().getTime(), item.getPrice()));

				}

				);

				cartItemRepository.deleteAllByUserId(userId);
				return "";
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}
	
	
	@GetMapping("/artist/pay/success")
	public String artistSuccessPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
			@RequestParam int userId) {
		try {
			Payment payment = service.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
			
				boughtSongRepository.updatePayslip(userId);	
				boughtSongRepository.upadteBoughtSong(userId);
				return "";
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}

}
