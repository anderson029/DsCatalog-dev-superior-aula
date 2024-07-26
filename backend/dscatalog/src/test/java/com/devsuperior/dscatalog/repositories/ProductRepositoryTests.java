package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.Factory;
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

    private long countTotalProducts;

    @BeforeEach
    public void seteup(){
        exintingId = 1L;
        idNotExist = 123456L;
        countTotalProducts = 25L;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts +1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExist(){
        repository.deleteById(exintingId);

        Optional<Product> result = repository.findById(exintingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findShouldProductWhenIdExists (){

        Optional<Product> productOpt = repository.findById(exintingId);

        Assertions.assertEquals(exintingId, productOpt.get().getId());
        Assertions.assertNotNull(productOpt);
        Assertions.assertTrue(productOpt.isPresent());

    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Optional<Product> result = repository.findById(idNotExist);

        Assertions.assertTrue(result.isEmpty());

//        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//            Optional<Product> productOpt = repository.findById(idNotExist);
//        } );
    }
}
