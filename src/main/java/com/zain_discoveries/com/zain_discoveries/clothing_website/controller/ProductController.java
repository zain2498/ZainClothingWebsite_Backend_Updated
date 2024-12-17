package com.zain_discoveries.com.zain_discoveries.clothing_website.controller;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.ProductException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Product;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.CreateProductRequest;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        Product product = productService.createProduct(createProductRequest);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        String result = "";
        try {
            result = productService.deleteProduct(productId);
        } catch (ProductException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestParam Product product) throws ProductException {
        Product updatedProduct = productService.updateProduct(productId, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<Product>> findProductByCategory(@PathVariable String category) throws ProductException {
        List<Product> products = productService.findProductByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category, @RequestParam List<String> color,
                                                                      @RequestParam List<String> size, @RequestParam Integer minPrice, @RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort,
                                                                      @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {

        Page<Product> res = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
        return ResponseEntity.ok(res);

    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchforProduct(@RequestParam String product) {
        List<Product> products = productService.searchProduct(product);
        return ResponseEntity.ok(products);
    }
}
