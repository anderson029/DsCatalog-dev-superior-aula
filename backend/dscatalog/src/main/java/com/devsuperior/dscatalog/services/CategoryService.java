package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTOs.CategoryRequestDto;
import com.devsuperior.dscatalog.DTOs.CategoryResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundExcepetion;
import jakarta.persistence.EntityNotFoundException;
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
        entityCategoryOpt.orElseThrow(()-> new ResourceNotFoundExcepetion("Categoria não encontrada")); /*Estanciando uma execeção para tratar erros*/
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

    @Transactional // é uma forma de garantir a atomicidade, consistência, isolamento e durabilidade (ACID) das operações em um banco de dados.
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
//        OBS: getReferenceById= é criado uma estância em memória do objeto sem "bater" no banco de dados, somente quando salvar é que de fato a applicação chama o banco de dados
        try {
            Category entity = categoryRepository.getReferenceById(id);
            entity.setName(categoryRequestDto.getName());
            entity = categoryRepository.save(entity);
            return new CategoryResponseDTO(entity);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundExcepetion("Id não existe " + id);
        }
    }
}