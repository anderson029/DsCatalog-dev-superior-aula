package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTOs.CategoryResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll(){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(CategoryResponseDTO::new).collect(Collectors.toList());
//        return categoryList.stream().map(list -> new CategoryResponseDTO(list)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long id) {
        Optional<Category> entityCategoryOpt = categoryRepository.findById(id);
         if (entityCategoryOpt.isPresent()){
            return new CategoryResponseDTO(entityCategoryOpt.get());
         }
         return null;
    }
}