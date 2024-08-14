package com.devsuperior.dscatalog.resources.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class FieldMessage implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private String name;
  private String message;

  public FieldMessage() {
  }

  public FieldMessage(String name, String message) {
    this.name = name;
    this.message = message;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
