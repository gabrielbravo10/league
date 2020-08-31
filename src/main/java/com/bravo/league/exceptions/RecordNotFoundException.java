package com.bravo.league.exceptions;


public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException() {
        super("error.notFound");
    }

    public RecordNotFoundException(String mensagem) {

        super(mensagem);
    }
}
