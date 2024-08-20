package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTOs.UserDTO;
import com.devsuperior.dscatalog.DTOs.UserInsertDTO;
import com.devsuperior.dscatalog.DTOs.UserUpdateDTO;
import com.devsuperior.dscatalog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
     Page listUsers = userService.findAllPaged(pageable);
     return ResponseEntity.status(HttpStatus.OK).body(listUsers);
  }

  @PostMapping
  public ResponseEntity<UserDTO> createUser (@Valid @RequestBody UserInsertDTO userInsertDTO) {
    UserDTO userDTO = userService.createUser(userInsertDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO UserUpdateDTO){

    UserDTO userUpdated = userService.updateUser(id, UserUpdateDTO);

    return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
  }
  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Long id){
    UserDTO user = userService.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(user);
  }
  @DeleteMapping(value = "/{id}")
  public void deleteUser(@PathVariable Long id){
    userService.deleteUser(id);
  }
}
