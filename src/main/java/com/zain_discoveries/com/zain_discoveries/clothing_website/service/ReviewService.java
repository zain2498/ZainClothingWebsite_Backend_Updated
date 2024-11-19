package com.zain_discoveries.com.zain_discoveries.clothing_website.service;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.ProductException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Review;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest request, User user) throws ProductException;

    public List<Review> getAllProductsReview(Long reviewId);
}
