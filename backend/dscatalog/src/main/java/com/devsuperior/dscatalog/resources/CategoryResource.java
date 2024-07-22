package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTOs.CategoryRequestDto;
import com.devsuperior.dscatalog.DTOs.CategoryResponseDTO;
import com.devsuperior.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> findall(Pageable pageable){
        Page<CategoryResponseDTO> list = categoryService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> findByID(@PathVariable Long id){
        CategoryResponseDTO catDto = categoryService.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(catDto);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDto categoryRequestDto){
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDto);

//      Inserindo location com patch
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(categoryResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDto categoryRequestDto){
        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, categoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}