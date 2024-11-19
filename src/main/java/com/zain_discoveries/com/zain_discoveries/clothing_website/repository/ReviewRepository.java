package com.zain_discoveries.com.zain_discoveries.clothing_website.repository;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("Select r from review r where r.review_id=:reviewId")
    public List<Review> getAllProductReview(@Param("reviewId") Long reviewId);
}
