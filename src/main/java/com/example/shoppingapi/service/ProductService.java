package com.example.shoppingapi.service;

import com.example.shoppingapi.entity.Product;

import java.util.List;

public interface ProductService {
    void createProduct(Product product);
    Product getProductDetailById(Long id);
    List<Product> getAllProduct();
}
