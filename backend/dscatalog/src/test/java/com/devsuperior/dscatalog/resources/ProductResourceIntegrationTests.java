package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.services.ProductService;
import org.junit.jupiter.api.Assertions;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceIntegrationTests {

  @Autowired
  private MockMvc mockMvc;
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
}
