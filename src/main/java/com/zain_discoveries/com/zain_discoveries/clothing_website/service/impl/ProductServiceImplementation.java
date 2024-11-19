package com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.ProductException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Category;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Product;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.CategoryRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.ProductRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.CreateProductRequest;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.ProductService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private UserService userService;

    public ProductServiceImplementation(ProductRepository productRepository, CategoryRepository categoryRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest createProductRequest) {
        Category topLavelCategory = categoryRepository.findByName(createProductRequest.getTopLevelCategory());
        if (topLavelCategory == null) {
            topLavelCategory = new Category();
            topLavelCategory.setName(createProductRequest.getTopLevelCategory());
            topLavelCategory.setLevel(1);

            topLavelCategory = categoryRepository.save(topLavelCategory);
        }

        Category secondLevelCategory = categoryRepository.findByNameAndParant(createProductRequest.getSecondLevelCategory(), topLavelCategory.getName());
        if (secondLevelCategory == null) {
            secondLevelCategory = new Category();
            secondLevelCategory.setName(createProductRequest.getSecondLevelCategory());
            secondLevelCategory.setLevel(2);
            secondLevelCategory.setParentCategory(topLavelCategory);

            secondLevelCategory = categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevelCategory = categoryRepository.findByNameAndParant(createProductRequest.getThirdLevelCategory(), secondLevelCategory.getName());
        if (thirdLevelCategory == null) {
            thirdLevelCategory = new Category();
            thirdLevelCategory.setName(createProductRequest.getThirdLevelCategory());
            thirdLevelCategory.setLevel(3);
            thirdLevelCategory.setParentCategory(secondLevelCategory);
            thirdLevelCategory = categoryRepository.save(thirdLevelCategory);
        }

        Product createProduct = new Product();
        createProduct.setTitle(createProductRequest.getTitle());
        createProduct.setColor(createProductRequest.getColor());
        createProduct.setDescription(createProductRequest.getDescription());
        createProduct.setDiscountedPrice(createProductRequest.getDiscountedPrice());
        createProduct.setDiscountPercent(createProductRequest.getDiscountPercent());
        createProduct.setBrand(createProductRequest.getBrand());
        createProduct.setImageUrl(createProductRequest.getImageUrl());
        createProduct.setPrice(createProductRequest.getPrice());
        createProduct.setSizes(createProductRequest.getSizes());
        createProduct.setQuantity(createProductRequest.getQuantity());
        createProduct.setCreatedAt(LocalDateTime.now());
        createProduct.setCategory(thirdLevelCategory);

        Product savedProduct = productRepository.save(createProduct);
        System.out.println("Create Product - " + createProduct);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product is successfully deleted";
    }

    @Override
    public Product updateProduct(Long productId, Product productRequest) throws ProductException {
        Product product = findProductById(productId);
        if(product.getQuantity() >0){
            product.setQuantity(productRequest.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> product= productRepository.findById(productId);
        if(product.isPresent()){
            return product.get();
        }
        throw  new ProductException("Product is not find with the given productId");
    }

    @Override
    public List<Product> findProductByCategory(String category) throws ProductException {
        List<Product> product = findProductByCategory(category);
        if(product.size() > 0){
            return  product;
        }
        throw new ProductException("There are no products in this category");
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, int minPrice, int maxPrice, int minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        List<Product> products = productRepository.fileterProducts(category,minPrice,maxPrice,minDiscount,sort);
        if(!colors.isEmpty()){
            products = products.stream().filter(p-> colors.stream().anyMatch(c-> c.equalsIgnoreCase(p.getColor()))).toList();
        }

        if (stock.equalsIgnoreCase("in_stock")){
            products = products.stream().filter(p-> p.getQuantity()>0).toList();
        }else if (stock.equalsIgnoreCase("out_of_stock")){
            products = products.stream().filter(p-> p.getQuantity() <1).collect(Collectors.toList());
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex+pageable.getPageSize(), products.size());
        List<Product> pageContent = products.subList(startIndex,endIndex);
        return new PageImpl<>(pageContent,pageable,products.size());
    }

    @Override
    public List<Product> searchProduct(String q) {
        return null;
    }
}
