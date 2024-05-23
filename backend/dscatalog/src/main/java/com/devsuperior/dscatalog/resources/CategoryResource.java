package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTOs.CategoryResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findall(){
        List<CategoryResponseDTO> list = categoryService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> findByID(@PathVariable Long id){
        CategoryResponseDTO catDto = categoryService.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(catDto);
    }
}