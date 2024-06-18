package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTOs.CategoryRequestDto;
import com.devsuperior.dscatalog.DTOs.CategoryResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseExcepetion;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll(){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(CategoryResponseDTO::new).collect(Collectors.toList());
//        return categoryList.stream().map(category -> new CategoryResponseDTO(category)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long id) {
        Optional<Category> entityCategoryOpt = categoryRepository.findById(id);
        entityCategoryOpt.orElseThrow(()-> new ResourceNotFoundException("Category not found")); /*Estanciando uma execeção para tratar erros*/
        return new CategoryResponseDTO(entityCategoryOpt.get());
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO createCategory(CategoryRequestDto categoryRequestDto) {
//        Category categoryEntity = new Category(null, categoryRequestDto.getName());
        var categoryEntity = ((Supplier<Category>) () -> new Category(null, categoryRequestDto.getName())).get();

        Category response = categoryRepository.save(categoryEntity);
        return new CategoryResponseDTO(response);
    }

    @Transactional // é uma forma de garantir a atomicidade, consistência, isolamento e durabilidade (ACID) das operações em um banco de dados.
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
//        OBS: getReferenceById= é criado uma estância em memória do objeto sem "bater" no banco de dados, somente quando salvar é que de fato a applicação chama o banco de dados
        try {
            Category entity = categoryRepository.getReferenceById(id);
            entity.setName(categoryRequestDto.getName());
            entity = categoryRepository.save(entity);
            return new CategoryResponseDTO(entity);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    public void deleteCategory (Long id){
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
            categoryRepository.delete(category);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseExcepetion("Integrity violation");
        }
    }
}