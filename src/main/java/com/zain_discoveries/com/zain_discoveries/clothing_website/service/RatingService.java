package com.zain_discoveries.com.zain_discoveries.clothing_website.service;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Rating;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest request, User user) throws Exception;
    public List<Rating> getAllProductsRating(Long productId);
}
