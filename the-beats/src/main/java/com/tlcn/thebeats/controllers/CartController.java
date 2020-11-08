package com.tlcn.thebeats.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tlcn.thebeats.models.CartItem;
import com.tlcn.thebeats.payload.request.AddToCartRequest;
import com.tlcn.thebeats.repository.CartItemRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/cart")
public class CartController {

	@Autowired
	private CartItemRepository cartItemRepository;

	@GetMapping("/all")
	public List<CartItem> getAllCart() {
		return cartItemRepository.findAll();
	}
	
	@GetMapping("/{userId}")
	public List<CartItem> getUserCart(@PathVariable int userId)
	{
		return this.cartItemRepository.findByUserId(userId);
	}

	@PostMapping("/addtocart")
	public int addToCart(@RequestBody AddToCartRequest addToCartRequest) {
		Optional<CartItem> item = cartItemRepository.findByUserIdAndSongId(addToCartRequest.getUserId(),
				addToCartRequest.getSongId());

		if (!item.isPresent()) {
			CartItem cartItem = new CartItem(new Date(), addToCartRequest.getUserId(), addToCartRequest.getPrice(),
					addToCartRequest.getSongId(), addToCartRequest.getSongName());
			cartItemRepository.save(cartItem);

		} else

			cartItemRepository.delete(item.get());

		return cartItemRepository.countByUserId(addToCartRequest.getUserId());

	}
	
	@GetMapping("/count/{userId}")
	public int getTotalItem(@PathVariable int userId)
	{
		return cartItemRepository.countByUserId(userId);
	}

	@DeleteMapping("/delete/{cartId}")
	public Map<String, Boolean> removeItem(@PathVariable int cartId) {
		Optional<CartItem> item = cartItemRepository.findById(cartId);
		if (!item.isPresent())
			throw new RuntimeException("Item not found");
		cartItemRepository.deleteById(cartId);
		Map<String, Boolean> respone = new HashMap<String, Boolean>();
		respone.put("deleted", true);
		return respone;

	}
}
