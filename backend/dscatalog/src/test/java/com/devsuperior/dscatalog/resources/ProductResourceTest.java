package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTOs.ProductRequestDto;
import com.devsuperior.dscatalog.DTOs.ProductResponseDTO;
import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.DataBaseExcepetion;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductResource.class) // Indica que o teste é para um controlador específico
public class ProductResourceTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  private ProductResponseDTO productResponseDTO;

  @Autowired
  private ObjectMapper objectMapper;

  private ProductRequestDto productRequestDTO;

  private PageImpl<ProductResponseDTO> page;

  @BeforeEach
  public void setup(){
    productRequestDTO = Factory.createProductDTO();
    productResponseDTO = Factory.createProductResponseDTO();

  }

  @Test
  void findAllShouldRetunrPageProductResponseDTO() throws Exception {

    page = new PageImpl<>(List.of(productResponseDTO)); // PageImpl permiti instaciar uma lista pagínas por isso não utilizamo o Pageable.

    when(productService.findAllPaged(any())).thenReturn(page);

     ResultActions result = mockMvc.perform((RequestBuilder)get("/products")
             .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
  void findByIdShouldReturnProductResponseDTOWhenIdExists() throws Exception{
    Long idExists = 1L;
    when(productService.findById(any())).thenReturn(productResponseDTO);

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
    when(productService.findById(any())).thenThrow(ResourceNotFoundException.class);

    ResultActions result = mockMvc.perform(get("/products/{id}", idNotExists).accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  void updateProductShouldReturnProductResponseDTOWhenIdExists() throws Exception{
    Long idExists = 1L;
    String jsonBody = objectMapper.writeValueAsString(productResponseDTO);

    when(productService.updateProduct(eq(idExists), any())).thenReturn(productResponseDTO); // podemos usa o <eq> para não ter incompatibilidade entre Matchers e Argumentos Reais
//    when(productService.updateProduct(anyLong(), any())).thenReturn(productResponseDTO);

    ResultActions result = mockMvc.perform(put("/products/{id}", idExists)
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.name").value("Phone"));
    result.andExpect(jsonPath("$.categories[0].name").value("Eletrônicos"));
  }

  @Test
  void updateProductShouldThrowsResourceNotFoundExceptionWhenIdNotExists() throws Exception{
    Long idNotExists = 2L;

    String jsonBody = objectMapper.writeValueAsString(productResponseDTO);

    when(productService.updateProduct(anyLong(), any())).thenThrow(ResourceNotFoundException.class);

    ResultActions result = mockMvc.perform(put("/products/{id}",idNotExists)
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
    result.andExpect(jsonPath("$.status").value("404"));
    result.andExpect(jsonPath("$.error").value("Resource not found"));
  }

  @Test
  void deleteProductShouldReturnNoContentWhenIdExists() throws Exception {
    Long idExists = 2L;

    doNothing().when(productService).deleteProduct(idExists);

    ResultActions result = mockMvc.perform(delete("/products/{id}", idExists));

    result.andExpect(status().isNoContent());
  }

  @Test
  void deleteProductShouldThrowsResourceNotFoundExceptionWhenIdNotExists() throws Exception {
    Long idNotExists = 2L;

    doThrow(ResourceNotFoundException.class).when(productService).deleteProduct(idNotExists);

    ResultActions result = mockMvc.perform(delete("/products/{id}",idNotExists));

    result.andExpect(status().isNotFound());
  }

  @Test
  void deleteProductShouldThrowsDataIntegrityViolationException() throws Exception {
    Long idViolation = 3L;

    doThrow(DataBaseExcepetion.class).when(productService).deleteProduct(idViolation);

    ResultActions result = mockMvc.perform(delete("/products/{id}",idViolation));

    result.andExpect(status().isBadRequest());
    result.andExpect(jsonPath("$.error").value("Database exception"));
  }

  @Test
  void createProductShouldReturnProductResponseDTOWhenSendRequestDTO() throws Exception{

   String json =  objectMapper.writeValueAsString(productRequestDTO);

    when(productService.createProduct(any())).thenReturn(productResponseDTO);

    ResultActions result = mockMvc.perform(post("/products")
            .accept(MediaType.APPLICATION_JSON)
            .content(json).contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated());
  }
}