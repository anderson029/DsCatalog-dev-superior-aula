package com.devsuperior.dscatalog.DTOs;

import com.devsuperior.dscatalog.entities.Category;

public class CategoryRequestDto {
    private String name;

    public CategoryRequestDto() {
    }

    public CategoryRequestDto(Category category) {
        this.name = category.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
