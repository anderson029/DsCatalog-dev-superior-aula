package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServicesIntegrationTests {

  @Autowired
  private ProductService productService;
  @Autowired
  private ProductRepository productRepository;

  private Long idExists;
  private Long idNotExistis;
  private Long countTotalProducts;
  @BeforeEach
  public void setup() throws Exception{
    idExists = 1L;
    idNotExistis =1000L;
    countTotalProducts = 25L;
  }

  @Test
  void deleteProductShouldDeleteResourcesWhenIdExists() throws Exception{
    productService.deleteProduct(idExists);

    Assertions.assertEquals(countTotalProducts - 1, productRepository.count());
  }

  @Test
  void deleteProductShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExists() throws Exception{

    Assertions.assertThrows(ResourceNotFoundException.class, () ->{
      productService.deleteProduct(idNotExistis);
    });
  }
}



