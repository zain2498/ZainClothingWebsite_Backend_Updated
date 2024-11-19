package com.zain_discoveries.com.zain_discoveries.clothing_website.repository;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c From cart c WHERE c.userId =:userId")
    public Cart findCartByUserId(@Param("userId") Long userId);
}
