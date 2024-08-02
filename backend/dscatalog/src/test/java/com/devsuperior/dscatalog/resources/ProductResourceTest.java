package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTOs.ProductResponseDTO;
import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.services.ProductService;
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

  }

  @Test
  void findAllShouldRetunrPageProductResponseDTO() throws Exception {
    productDto = Factory.createProductResponseDTO();
    page = new PageImpl<>(List.of(productDto)); // PageImpl permiti instaciar uma lista pagínas por isso não utilizamo o Pageable.

    when(ProductService.findAllPaged(any())).thenReturn(page);

     ResultActions result = mockMvc.perform((RequestBuilder)get("/products")
             .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }
}
