package com.example.shoppingapi.service;

import com.example.shoppingapi.dao.ProductRepository;
import com.example.shoppingapi.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getProductDetailById(Long id) {
        var product =  productRepository.findById(id);

        if (product.isEmpty()) {
           return null;
        }

        return product.get();
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
