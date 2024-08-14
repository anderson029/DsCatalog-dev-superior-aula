package com.devsuperior.dscatalog.services.validation;

import com.devsuperior.dscatalog.DTOs.UserRequestDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.resources.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserRequestDTO> {

  @Autowired
  private UserRepository userRepository;

  @Override
  public void initialize(UserInsertValid ann) {
  }

  @Override
  public boolean isValid(UserRequestDTO dto, ConstraintValidatorContext context) {

    List<FieldMessage> list = new ArrayList<>();
    User user = userRepository.findByEmail(dto.getEmail());
    if (user != null){
      list.add(new FieldMessage("email", "E-mail já cadastrado"));
    }

    for (FieldMessage e : list) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getName())
              .addConstraintViolation();
    }
    return list.isEmpty();
  }
}
