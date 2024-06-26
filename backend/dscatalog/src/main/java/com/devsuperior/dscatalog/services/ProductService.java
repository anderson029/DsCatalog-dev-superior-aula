package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTOs.CategoryRequestDto;
import com.devsuperior.dscatalog.DTOs.CategoryResponseDTO;
import com.devsuperior.dscatalog.DTOs.ProductRequestDto;
import com.devsuperior.dscatalog.DTOs.ProductResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
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

    @Autowired
    private CategoryRepository categoryRepository;

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
        Product newEntity = new Product();
        copyDtoToEntity(ProductRequestDto, newEntity);
        Product entity = ProductRepository.save(newEntity);
        return new ProductResponseDTO(entity, entity.getCategories());
    }

    @Transactional // é uma forma de garantir a atomicidade, consistência, isolamento e durabilidade (ACID) das operações em um banco de dados.
    public ProductResponseDTO updateProduct(Long id, ProductRequestDto ProductRequestDto) {
//        OBS: getReferenceById= é criado uma estância em memória do objeto sem "bater" no banco de dados, somente quando salvar é que de fato a applicação chama o banco de dados
        try {
            Product entity = ProductRepository.getReferenceById(id);
            copyDtoToEntity(ProductRequestDto, entity);
            entity = ProductRepository.save(entity);
            return new ProductResponseDTO(entity, entity.getCategories());
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

    private void copyDtoToEntity(ProductRequestDto dto, Product entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());
        entity.setDate(dto.getDate());

        entity.getCategories().clear(); //Garantir que a lista de categorias esteja vazia.

        for (CategoryResponseDTO catDto : dto.getCategories()){
            Optional<Category> category = categoryRepository.findById(catDto.getId()); //poderia usar o getone porém não funciona
            Category categoryEntity = category.get();
            entity.getCategories().add(categoryEntity);
        }
    }
}