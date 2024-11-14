package br.fatec.bd4.web.exception;

public class InvalidPasswords extends RuntimeException{
    public InvalidPasswords(String message){
        super(message);
    }
}
