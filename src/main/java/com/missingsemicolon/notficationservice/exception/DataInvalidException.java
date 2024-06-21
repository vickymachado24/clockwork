package com.missingsemicolon.notficationservice.exception;

import java.util.Map;

public class DataInvalidException extends Exception{

    String message;

    public DataInvalidException(String message){
        super(message);
    }

}
