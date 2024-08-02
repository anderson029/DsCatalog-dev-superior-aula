package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTOs.ProductResponseDTO;
import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductResource.class) // Indica que o teste é para um controlador específico
public class ProductResourceTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService ProductService;

  private ProductResponseDTO productDto;

  private PageImpl<ProductResponseDTO> page;

  @BeforeEach
  public void setup(){
    productDto = Factory.createProductResponseDTO();
  }

  @Test
  void findAllShouldRetunrPageProductResponseDTO() throws Exception {

    page = new PageImpl<>(List.of(productDto)); // PageImpl permiti instaciar uma lista pagínas por isso não utilizamo o Pageable.

    when(ProductService.findAllPaged(any())).thenReturn(page);

     ResultActions result = mockMvc.perform((RequestBuilder)get("/products")
             .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
  void findByIdShouldReturnProductResponseDTOWhenIdExists() throws Exception{
    Long idExists = 1L;
    when(ProductService.findById(any())).thenReturn(productDto);

    ResultActions result = mockMvc.perform(get("/products/{id}", idExists).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isAccepted());
    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.name").exists());
    result.andExpect(jsonPath("$.description").exists());
    result.andExpect(jsonPath("$.price").exists());
    result.andExpect(jsonPath("$.imgUrl").exists());
    result.andExpect(jsonPath("$.date").exists());
    result.andExpect(jsonPath("$.categories[0].name").exists());
    result.andExpect(jsonPath("$.categories[?(@.name=='Eletrônicos')]").exists());
  }

  @Test
  void findByIdShouldReturnNotFoundWhenIdNotExists() throws Exception {
    Long idNotExists = 2L;

    when(ProductService.findById(any())).thenThrow(ResourceNotFoundException.class);

    ResultActions result = mockMvc.perform(get("/products/{id}", idNotExists).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }
}
