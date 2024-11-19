package com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.ProductException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Product;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Review;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.ReviewRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.ReviewRequest;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.ProductService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {

    private ProductService productService;
    private ReviewRepository reviewRepository;

    public ReviewServiceImplementation(ProductService productService, ReviewRepository reviewRepository){
        this.productService = productService;
        this.reviewRepository = reviewRepository;
    }
    @Override
    public Review createReview(ReviewRequest request, User user) throws ProductException {
        Product product = productService.findProductById(request.getProductId());
        Review review = new Review();
        review.setReview(request.getReview());
        review.setUser(user);
        review.setProduct(product);
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllProductsReview(Long reviewId) {
        return reviewRepository.getAllProductReview(reviewId);
    }
}
