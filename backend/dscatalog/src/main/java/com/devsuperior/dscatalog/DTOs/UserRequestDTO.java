package com.devsuperior.dscatalog.DTOs;

public class UserRequestDTO extends UserDTO {

  private String password;

  UserRequestDTO(){
    super();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
