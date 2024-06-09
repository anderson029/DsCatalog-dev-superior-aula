package com.devsuperior.dscatalog.DTOs;


import com.devsuperior.dscatalog.entities.Category;

import java.io.Serial;
import java.io.Serializable;

public class CategoryResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long Id;
    private String name;

    public CategoryResponseDTO(Category category) {
        this.Id = category.getId();
        this.name = category.getName();
    }

    public CategoryResponseDTO(Long id, String name) {
        Id = id;
        this.name = name;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }
}
