package com.tlcn.thebeats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tlcn.thebeats.models.PlacedOrder;

public interface PlacedOrderRepository extends JpaRepository<PlacedOrder, Integer>{

}
