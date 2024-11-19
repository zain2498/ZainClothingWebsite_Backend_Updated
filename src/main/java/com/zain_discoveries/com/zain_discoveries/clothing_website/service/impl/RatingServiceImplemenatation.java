package com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Product;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Rating;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.RatingRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.RatingRequest;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.ProductService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.RatingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImplemenatation implements RatingService {
    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImplemenatation(RatingRepository ratingRepository, ProductService productService){
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }
    @Override
    public Rating createRating(RatingRequest request, User user) throws Exception {
        Product product = productService.findProductById(request.getProductId());
        Rating rating = new Rating();
        rating.setRating(request.getRating());
        rating.setUser(user);
        rating.setProduct(product);
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllProductsRating(Long productId) {
        return ratingRepository.getAllProductRatings(productId);
    }
}
