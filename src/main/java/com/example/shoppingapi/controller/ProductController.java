package com.example.shoppingapi.controller;

import com.example.shoppingapi.dto.CreateProductResponse;
import com.example.shoppingapi.dto.DefaultRequest;
import com.example.shoppingapi.dto.DefaultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @PostMapping("/create")
    DefaultResponse createProduct(@RequestBody DefaultRequest request) {
        return new CreateProductResponse();
    }

    @GetMapping("/get/{productId}")
    DefaultResponse getProduct(@PathVariable("productId") Long productId) {
        return new CreateProductResponse();
    }

    @GetMapping("/get/all")
    DefaultResponse getProductList() {
        return new CreateProductResponse();
    }

    @GetMapping("/get/{productCategory}")
    DefaultResponse getProductPage(@RequestParam("page") Integer page) {
        return new CreateProductResponse();
    }
}
