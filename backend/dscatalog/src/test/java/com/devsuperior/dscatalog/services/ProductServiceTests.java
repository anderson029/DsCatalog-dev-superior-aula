package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTOs.ProductRequestDto;
import com.devsuperior.dscatalog.DTOs.ProductResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseExcepetion;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
  @InjectMocks
  private ProductService productService;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private CategoryRepository categoryRepository;

  private Product product;

  private ProductRequestDto productRequestDto;

  @BeforeEach
  public void setup() {
    product = Factory.createProduct();
    productRequestDto = Factory.createProductDTO();
  }


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
     PageImpl<Product> page = new PageImpl<>(List.of(product));
     when(productRepository.findAll((Pageable) any())).thenReturn(page);

     Pageable pageable = PageRequest.of(0,10);
     Page<ProductResponseDTO> result = productService.findAllPaged(pageable);

     Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);

     Assertions.assertNotNull(result);
     Mockito.verify(productRepository).findAll(pageable);
  }

  @Test
  void findByIdShouldReturnProductResponseDTOWhenIdExists(){
    Long idExist = 1L;

    Mockito.when(productRepository.findById(idExist)).thenReturn(Optional.of(product));

    ProductResponseDTO result = productService.findById(idExist);

    verify(productRepository, times(1)).findById(idExist);
    Assertions.assertNotNull(productRepository.findById(idExist));
    Assertions.assertEquals(1L, result.getId());
    Assertions.assertEquals("Phone", result.getName());
    Assertions.assertNotNull(result);
  }

  @Test
  void findByIdShouldThrowsResourceNotFoundExceptionWhenIdNotExists(){
    Long idNotExist = 123L;

    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      productService.findById(idNotExist);
    });
  }

  @Test
  void updateProductShouldReturnProductUpdatedWhenIDexists(){
    when(productRepository.getReferenceById(anyLong())).thenReturn(product);
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category(3L,"Eletronics")));
    when(productRepository.save(any())).thenReturn(product);

    ProductResponseDTO result = productService.updateProduct(product.getId(), productRequestDto);

    verify(productRepository, times(1)).getReferenceById(product.getId());
    verify(categoryRepository).findById(2L);
    verify(productRepository).save(product);

    Assertions.assertEquals(1L, result.getId());
    Assertions.assertEquals(3L, result.getCategories().get(0).getId());
    Assertions.assertEquals("Phone", result.getName());
  }

  @Test
  void updateProductShouldTrowsEntityNotFoundExceptionWhenIdNotExists(){

    Mockito.doThrow(EntityNotFoundException.class).when(productRepository).getReferenceById(123L);

    Assertions.assertThrows(ResourceNotFoundException.class, () ->{
      productService.updateProduct(123L, productRequestDto);
    });
  }
}