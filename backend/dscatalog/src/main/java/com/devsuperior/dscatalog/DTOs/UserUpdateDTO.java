package com.devsuperior.dscatalog.DTOs;

import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.services.validation.UserInsertValid;
import com.devsuperior.dscatalog.services.validation.UserUpdateValid;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
}
