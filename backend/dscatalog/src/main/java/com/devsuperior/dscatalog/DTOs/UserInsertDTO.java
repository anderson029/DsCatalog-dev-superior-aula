package com.devsuperior.dscatalog.DTOs;

import com.devsuperior.dscatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

@UserInsertValid
public class UserInsertDTO extends UserDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  @NotBlank(message = "A senha é obrigatória")
  private String password;

  UserInsertDTO() {
    super();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
