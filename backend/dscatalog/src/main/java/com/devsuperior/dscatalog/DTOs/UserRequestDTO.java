package com.devsuperior.dscatalog.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserRequestDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotBlank(message = "O Nome é obrigatório")
  private String firstName;
  private String lastName;

  @Email(message = "O e-mail tem que ser válido")
  private String email;

  private String password;

  private Set<RoleDTO> role = new HashSet<>();

  public UserRequestDTO() {
  }

  public UserRequestDTO(String firstName, String lastName, String email, String password, Set<RoleDTO> role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<RoleDTO> getRole() {
    return role;
  }

  public void setRole(Set<RoleDTO> role) {
    this.role = role;
  }
}
