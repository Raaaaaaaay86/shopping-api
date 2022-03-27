package com.example.shoppingapi.service;

import com.example.shoppingapi.dao.CartRepository;
import com.example.shoppingapi.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public Boolean addProduct(Product product, Long cartId) {
        var targetCartOptional = cartRepository.findById(cartId);

        if (targetCartOptional.isEmpty()) {
            return false;
        }
        var targetCart = targetCartOptional.get();

        var cartProductList = targetCart.getProducts();
        cartProductList.add(product);

        return null;
    }

    @Override
    public Boolean removeProduct(Product product, Long cartId) {
        var targetCartOptional = cartRepository.findById(cartId);

        if (targetCartOptional.isEmpty()) {
            return false;
        }
        var targetCart = targetCartOptional.get();
        var cartProductList = targetCart.getProducts();

        return cartProductList.remove(product);
    }

    @Override
    public List<Product> getProductList(Long cartId) {
        var targetCartOptional = cartRepository.findById(cartId);

        if (targetCartOptional.isEmpty()) {
            return  null;
        }
        var targetCart = targetCartOptional.get();

        return targetCart.getProducts();
    }
}
