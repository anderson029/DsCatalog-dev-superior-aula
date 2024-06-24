package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTOs.ProductRequestDto;
import com.devsuperior.dscatalog.DTOs.ProductResponseDTO;
import com.devsuperior.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService ProductService;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findall(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
            ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<ProductResponseDTO> list = ProductService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findByID(@PathVariable Long id){
        ProductResponseDTO catDto = ProductService.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(catDto);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDto ProductRequestDto){
        ProductResponseDTO ProductResponseDTO = ProductService.createProduct(ProductRequestDto);

//        Inserindo location com patch
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ProductResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(ProductResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto ProductRequestDto){
        ProductResponseDTO ProductResponseDTO = ProductService.updateProduct(id, ProductRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ProductResponseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        ProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}