package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTOs.ProductRequestDto;
import com.devsuperior.dscatalog.DTOs.ProductResponseDTO;
import com.devsuperior.dscatalog.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<ProductResponseDTO>> findall(Pageable pageable){
        Page<ProductResponseDTO> list = ProductService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findByID(@PathVariable Long id){
        ProductResponseDTO catDto = ProductService.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(catDto);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto){
        ProductResponseDTO ProductResponseDTO = ProductService.createProduct(productRequestDto);

//        Inserindo location com patch
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ProductResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(ProductResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto productRequestDto){
        ProductResponseDTO ProductResponseDTO = ProductService.updateProduct(id, productRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ProductResponseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        ProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}