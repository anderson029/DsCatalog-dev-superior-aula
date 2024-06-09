package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTOs.CategoryRequestDto;
import com.devsuperior.dscatalog.DTOs.CategoryResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
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
        entityCategoryOpt.orElseThrow(()-> new EntityNotFoundExcepetion("Categoria não encontrada")); /*Estanciando uma execeção para tratar erros*/
        return new CategoryResponseDTO(entityCategoryOpt.get());
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO createCategory(CategoryRequestDto categoryRequestDto) {
//        Category categoryEntity = new Category(null, categoryRequestDto.getName());
        Category categoryEntity = ((Supplier<Category>) () -> new Category(null, categoryRequestDto.getName())).get();

        Category response = categoryRepository.save(categoryEntity);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(response);
        return categoryResponseDTO;
    }
}