package com.zain_discoveries.com.zain_discoveries.clothing_website.service;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.ProductException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Product;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest createProductRequest);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product product) throws ProductException;

    public Product findProductById(Long productId) throws ProductException;

    public List<Product> findProductByCategory(String category) throws ProductException;

    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, int minPrice, int maxPrice, int minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

    List<Product> searchProduct(String q);

}
