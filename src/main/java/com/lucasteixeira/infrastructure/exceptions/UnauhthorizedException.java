package com.lucasteixeira.infrastructure.exceptions;


import org.springframework.security.core.AuthenticationException;

public class UnauhthorizedException extends AuthenticationException {
    public UnauhthorizedException(String mensagem){
        super(mensagem);
    }

    public UnauhthorizedException(String mensagem, Throwable throwable){
        super(mensagem, throwable);
    }
}
