package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository repository;

    private long exintingId;
    private long idNotExist;

    @BeforeEach
    public void seteup(){
        exintingId = 1L;
        idNotExist = 123456L;

    }
    @Test
    public void deleteShouldDeleteObjectWhenIdExist(){
        repository.deleteById(exintingId);

        Optional<Product> result = repository.findById(exintingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExtst(){

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            repository.deleteById(idNotExist);
        } );
    }
}
