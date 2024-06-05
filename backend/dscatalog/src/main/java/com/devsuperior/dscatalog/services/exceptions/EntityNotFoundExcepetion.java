package com.devsuperior.dscatalog.services.exceptions;


/*Classe para exibir execeções
* Após isso vamos criar uma classe auxiliar(ResourcesExceptionsHandler), para tratar as exceções e retornar respostas padronizadas
*/
public class EntityNotFoundExcepetion extends RuntimeException{
    public EntityNotFoundExcepetion(String msg){
        super(msg);
    }
}
