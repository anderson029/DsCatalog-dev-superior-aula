package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTOs.ProductRequestDto;
import com.devsuperior.dscatalog.factory.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceIntegrationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objMapper;
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
  void findallShouldReturnSortedPageWhenSortByName() throws Exception {
    Pageable pageable = PageRequest.of(0,10, Sort.by("name"));

    ResultActions result = mockMvc.perform(get("/products?page=0&sort=name&size=10")
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.content").exists());
    result.andExpect(jsonPath("$.totalElements").value(countTotalProducts));
    result.andExpect(jsonPath("$.content").isNotEmpty());
    result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
    result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
  }

  @Test
  void updateProductShouldReturnPageProductWhenIdExists() throws Exception {
    ProductRequestDto productRequestDto = Factory.createProductDTO();

    ResultActions result = mockMvc.perform(put("/products/{id}",idExists)
            .content(objMapper.writeValueAsString(productRequestDto))
            .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.id").value(idExists));
    result.andExpect(jsonPath("$.name").value(productRequestDto.getName()));
    result.andExpect(jsonPath("$.categories[0].name").value("Eletrônicos"));
  }

  @Test
  void updateProductShouldReturnNotFOundWhenIdNotExists() throws Exception {
    ProductRequestDto productRequestDto = Factory.createProductDTO();

    ResultActions result = mockMvc.perform(put("/products/{id}", idNotExistis)
            .content(objMapper.writeValueAsString(productRequestDto))
            .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }
}