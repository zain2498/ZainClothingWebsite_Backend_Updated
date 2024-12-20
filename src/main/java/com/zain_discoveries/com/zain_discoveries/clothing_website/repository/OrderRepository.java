package com.zain_discoveries.com.zain_discoveries.clothing_website.repository;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order AS o WHERE o.user.id = :userId ")
    public List<Order> findByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Order AS o where o.orderId = :orderId ")
    public Order findByOrderId(@Param("orderId") String orderId);
}
