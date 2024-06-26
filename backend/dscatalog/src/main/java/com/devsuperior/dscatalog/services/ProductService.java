package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTOs.ProductRequestDto;
import com.devsuperior.dscatalog.DTOs.ProductResponseDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseExcepetion;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class ProductService {

    @Autowired
    private ProductRepository ProductRepository;

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> ProductList = ProductRepository.findAll(pageRequest);
        return ProductList.map(ProductResponseDTO::new);
//        return ProductList.stream().map(Product -> new ProductResponseDTO(Product)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        Optional<Product> entityProductOpt = ProductRepository.findById(id);
        Product entity = entityProductOpt.orElseThrow(()-> new ResourceNotFoundException("Product not found")); /*Estanciando uma execeção para tratar erros*/
        return new ProductResponseDTO(entity, entity.getCategories());
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO createProduct(ProductRequestDto ProductRequestDto) {
//        Product ProductEntity = new Product(null, ProductRequestDto.getName());
        var ProductEntity = ((Supplier<Product>) () -> new Product(null, ProductRequestDto.getName())).get();

        Product response = ProductRepository.save(ProductEntity);
        return new ProductResponseDTO(response);
    }

    @Transactional // é uma forma de garantir a atomicidade, consistência, isolamento e durabilidade (ACID) das operações em um banco de dados.
    public ProductResponseDTO updateProduct(Long id, ProductRequestDto ProductRequestDto) {
//        OBS: getReferenceById= é criado uma estância em memória do objeto sem "bater" no banco de dados, somente quando salvar é que de fato a applicação chama o banco de dados
        try {
            Product entity = ProductRepository.getReferenceById(id);
            entity.setName(ProductRequestDto.getName());
            entity = ProductRepository.save(entity);
            return new ProductResponseDTO(entity);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    public void deleteProduct (Long id){
        try {
            Product Product = ProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
            ProductRepository.delete(Product);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseExcepetion("Integrity violation");
        }
    }
}