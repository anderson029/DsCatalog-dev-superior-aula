package com.devsuperior.dscatalog.services;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
  @InjectMocks
  private ProductService productService;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private CategoryRepository categoryRepository;


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
}