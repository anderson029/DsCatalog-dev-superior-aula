package com.devsuperior.dscatalog.DTOs;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private Double price;
    private String imgUrl;

    private Instant date;

    private List<CategoryRequestDto> categories = new ArrayList<>();

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, String description, Double price, String imgUrl, Instant date) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductRequestDto(Product entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.date = entity.getDate();
    }

    public ProductRequestDto(Product entity, Set<Category> categories) {
       this(entity); // chama o construtor que contenha essa entidade como parâmetro
        categories.forEach(cat -> this.categories.add(new CategoryRequestDto(cat)));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<CategoryRequestDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryRequestDto> categories) {
        this.categories = categories;
    }
}
