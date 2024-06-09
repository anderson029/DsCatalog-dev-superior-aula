package com.devsuperior.dscatalog.resources.exceptions;

import org.springframework.http.HttpStatus;

import java.time.Instant;

/*
Classe que sera usada para padronizar as exceções
* */
public class StandardError {

    private Instant timestamp;
    private HttpStatus status;
    private String error;
    private String message;
    private String patch;

    public StandardError() {
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }
}
