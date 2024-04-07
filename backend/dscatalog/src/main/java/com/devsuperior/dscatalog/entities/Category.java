package com.devsuperior.dscatalog.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/*Serializable = padrao do JAVA para o objeto possa ser convertido em bytes... */
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;

    public Category() {
    }
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /*equals compara um objeto e retorna true or false*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(getId(), category.getId());
    }
}
