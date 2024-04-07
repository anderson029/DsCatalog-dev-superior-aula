package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*resource implementa os controladores rest (recursos disponibilizados para aplicações )*/
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
    @GetMapping
    /*Tipo = Public
    * ResponseEntity= Objeto do spring que capsula retorna o response http
    * <Tipo do dado><Category>
     findall = nome do método*/
    public ResponseEntity<List<Category>> findall(){
        List<Category> list = new ArrayList<>();
        list.add(new Category(1L, "Books"));
        list.add(new Category(2L,"Electronics"));
        return ResponseEntity.ok().body(list);
    }
}