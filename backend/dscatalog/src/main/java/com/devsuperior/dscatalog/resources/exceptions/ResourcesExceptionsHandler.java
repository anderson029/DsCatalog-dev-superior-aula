package com.devsuperior.dscatalog.resources.exceptions;

import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundExcepetion;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

// Classe para manipular as exceções na camada de resources e retornar resposta padronizada
@ControllerAdvice //Anotations para capturar execeções do controller
public class ResourcesExceptionsHandler {

    @ExceptionHandler(ResourceNotFoundExcepetion.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundExcepetion e, HttpServletRequest request){
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.NOT_FOUND);
        err.setError("Resource not found");
        err.setMessage(e.getMessage());
        err.setPatch(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}
