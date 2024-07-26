package com.devsuperior.dscatalog.factory;

import com.devsuperior.dscatalog.DTOs.ProductRequestDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L,"Phone","Good phone", 800.0,"https://img.com/img.png", Instant.parse("2020-05-07T00:00:00Z"));
        product.getCategories().add(new Category(2L, "Eletrônicos"));
        return  product;
    }

    public static ProductRequestDto createProductDTO(){
       Product product = createProduct();
       return new ProductRequestDto(product, product.getCategories());
    }

}
