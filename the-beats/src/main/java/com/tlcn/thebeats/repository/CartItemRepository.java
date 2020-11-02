package com.tlcn.thebeats.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.CartItem;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

	public Optional<CartItem> findByUserIdAndSongId(int userId, int songId);
}
