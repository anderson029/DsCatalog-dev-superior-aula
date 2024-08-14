package com.devsuperior.dscatalog.DTOs;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(min = 5, max = 60, message = "Deve ter no minímo 5 a 60 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;

    @NotBlank(message = "Campo requerido")
    private String description;
    @Positive(message = "O Valor tem que ser positivo")
    private Double price;
    private String imgUrl;
    @PastOrPresent(message = "A data do produto não pode ser futura")
    private Instant date;

    private List<CategoryResponseDTO> categories = new ArrayList<>();

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
        categories.forEach(cat -> this.categories.add(new CategoryResponseDTO(cat)));
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

    public List<CategoryResponseDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponseDTO> categories) {
        this.categories = categories;
    }
}
