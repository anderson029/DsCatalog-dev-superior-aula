package com.devsuperior.dscatalog.DTOs;

import com.devsuperior.dscatalog.entities.Role;

import java.io.Serial;
import java.io.Serializable;

public class RoleDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;

  private String authority;

  public RoleDTO() {
  }

  public RoleDTO(Long id, String authority) {
    this.id = id;
    this.authority = authority;
  }

  public RoleDTO(Role role) {
    this.id = role.getId();
    this.authority = role.getAuthority();
  }
}
