package com.example.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ResourseNotFoundException extends RuntimeException{



    public ResourseNotFoundException(String resourseName,String fieldName,String fieldValue){

        super(String.format("%s not found with the given input data %s: '%s'",resourseName,fieldName,fieldValue));


    }
}
