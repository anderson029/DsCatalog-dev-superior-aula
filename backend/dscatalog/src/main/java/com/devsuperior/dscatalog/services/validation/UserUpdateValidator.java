package com.devsuperior.dscatalog.services.validation;

import com.devsuperior.dscatalog.DTOs.UserUpdateDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.resources.exceptions.FieldMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void initialize(UserUpdateValid ann) {
  }

  @Override
  public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {

    Long userId = null;

    Object uriVars = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE); // modo para consultar info da requisição

    if (uriVars instanceof Map) {
      @SuppressWarnings("unchecked")
      Map<String, String> uriVarsMap = (Map<String, String>) uriVars;
      // Usa o método get para acessar o valor
      userId = Long.parseLong(uriVarsMap.get("id"));
    }

    List<FieldMessage> list = new ArrayList<>();
    User user = userRepository.findByEmail(dto.getEmail());

    if (user != null && userId != user.getId()) {
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
