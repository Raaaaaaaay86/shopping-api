package com.example.shoppingapi.service;

import com.example.shoppingapi.entity.Product;

import java.util.List;

public interface CartService {
    Boolean addProduct(Product product, Long cartId);
    Boolean removeProduct(Product product, Long cartId);
    List<Product> getProductList(Long cartId);
}
