package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTOs.UserDTO;
import com.devsuperior.dscatalog.DTOs.UserRequestDTO;
import com.devsuperior.dscatalog.DTOs.UserResponseDTO;
import com.devsuperior.dscatalog.services.UserService;
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
  public ResponseEntity<UserResponseDTO> createUser (@RequestBody UserRequestDTO userRequestDTO) {
    UserResponseDTO userDTO = userService.createUser(userRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO ){

    UserResponseDTO userUpdated = userService.updateUser(id,userRequestDTO);

    return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
  }
  @GetMapping(value = "/{id}")
  public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
    UserResponseDTO user = userService.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteUser(@PathVariable Long id){
    userService.deleteUser(id);
  }
}
