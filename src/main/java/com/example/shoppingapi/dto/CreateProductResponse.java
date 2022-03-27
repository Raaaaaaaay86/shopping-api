package com.example.shoppingapi.dto;

import com.example.shoppingapi.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductResponse extends DefaultResponse {
    Product product;
}
