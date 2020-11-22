package com.tlcn.thebeats.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.CartItem;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

	public Optional<CartItem> findByUserIdAndSongId(int userId, int songId);
	public int countByUserId (int userId);
	public List<CartItem> findByUserId(int userId);
	 @Transactional
	public void deleteAllByUserId(int userId);
}
