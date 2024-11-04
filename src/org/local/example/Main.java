package org.local.example;

import org.local.mapper.Mapper;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Product product = new Product("123", "Headphone", "Wireless Bluetooth Headphone", BigDecimal.valueOf(400.00));
        ProductDTO dto = Mapper.transform(product, ProductDTO.class);
        System.out.println(dto);
    }
}