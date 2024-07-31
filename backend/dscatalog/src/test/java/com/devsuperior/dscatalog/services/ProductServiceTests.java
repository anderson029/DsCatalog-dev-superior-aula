package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTOs.ProductResponseDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseExcepetion;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
  @InjectMocks
  private ProductService productService;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private CategoryRepository categoryRepository;

  private Product product;


  @Test
  void deleteProductShouldDoNothingWhenIdExists(){
    Long idExist = 1L;

    Mockito.doNothing().when(productRepository).deleteById(idExist);

    Assertions.assertDoesNotThrow(()->{
      productService.deleteProduct(idExist);
    });

    Mockito.verify(productRepository, Mockito.times(1)).deleteById(idExist);
  }

  @Test
  void deleteProductShouldThrowExceptionWhenIdNotExists(){
    Long idNotExist = 123456L;

    Mockito.doThrow(ResourceNotFoundException.class).when(productRepository).deleteById(idNotExist);
    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      productService.deleteProduct(idNotExist);
    });

    Mockito.verify(productRepository, Mockito.times(1)).deleteById(idNotExist);
  }

  @Test
  void deleteProductShouldThrowDataBaseExcepetionWhenDependencyId(){
    Long dependencyId = 4L;

    Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependencyId);

    Assertions.assertThrows(DataBaseExcepetion.class,() ->{
      productService.deleteProduct(dependencyId);
    });

    Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependencyId);
  }

  @Test
  void findAllPagedShouldReturnPagedProducts(){
     product = Factory.createProduct();
     PageImpl<Product> page = new PageImpl<>(List.of(product));
     Mockito.when(productRepository.findAll((Pageable) any())).thenReturn(page);

     Pageable pageable = PageRequest.of(0,10);
     Page<ProductResponseDTO> result = productService.findAllPaged(pageable);

     Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);

     Assertions.assertNotNull(result);
     Mockito.verify(productRepository).findAll(pageable);
  }
}





