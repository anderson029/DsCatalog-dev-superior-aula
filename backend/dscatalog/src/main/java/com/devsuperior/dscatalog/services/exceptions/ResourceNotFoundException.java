package com.devsuperior.dscatalog.services.exceptions;


/*Classe para capturar execeções
* Após isso vamos criar uma classe auxiliar(ResourcesExceptionsHandler), para tratar as exceções e retornar respostas padronizadas.
*/
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
