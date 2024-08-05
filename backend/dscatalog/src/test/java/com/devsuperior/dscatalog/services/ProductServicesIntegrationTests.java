package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTOs.ProductResponseDTO;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

  @Test
  void findAllPagedShouldReturnPageWhenPage0Size10() throws Exception {
    Pageable pageable = PageRequest.of(0,10);
    Page<ProductResponseDTO> pageProducts = productService.findAllPaged(pageable);

    Assertions.assertNotNull(pageProducts);
    Assertions.assertFalse(pageProducts.isEmpty());
    Assertions.assertEquals(0, pageProducts.getNumber());
    Assertions.assertEquals(10, pageProducts.getSize());
    Assertions.assertEquals(25, pageProducts.getTotalElements());

  }

  @Test
  void findAllPagedShouldReturnPageEmptyWhenPageNotExists() throws Exception {
    Pageable pageable = PageRequest.of(4,10);
    Page<ProductResponseDTO> pageProducts = productService.findAllPaged(pageable);

    Assertions.assertTrue(pageProducts.isEmpty());
  }

  @Test
  void findAllPagedShouldReturnSortedPageWhenSortByName() throws Exception {
    Pageable pageable = PageRequest.of(0,10, Sort.by("name"));
    Page<ProductResponseDTO> pageProducts = productService.findAllPaged(pageable);

    Assertions.assertFalse(pageProducts.isEmpty());
    Assertions.assertEquals("Macbook Pro", pageProducts.getContent().get(0).getName());
  }
}



