package com.cherkashyn.vitalii.logparser.input;

import javax.validation.ConstraintViolation;
import java.io.FileNotFoundException;
import java.util.Set;

public class ReadException extends RuntimeException{
    public ReadException(String message){
        super(message);
    }
    public ReadException(FileNotFoundException realCause) {
        super(realCause);
    }

    public ReadException(Set<ConstraintViolation<Trace>> validationResult) {
        super(toString(validationResult));
    }

    private static String toString(Set<ConstraintViolation<Trace>> validationResult){
        StringBuilder message = new StringBuilder();
        for(ConstraintViolation<?> eachViolation:validationResult){
            message.append(eachViolation.getMessage());
            message.append(System.lineSeparator());
        }
        return message.toString();
    }
}
