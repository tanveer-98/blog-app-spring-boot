package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

// WE THROW THIS EXCEPTION WHENEVER WE WRITE SOME BUSINESS LOGIC OR VALIDATE REQUEST PARAMETERS

public class BlogAPIException  extends RuntimeException{
    private HttpStatus status;
    private String message;


    public BlogAPIException(HttpStatus status , String message){
        this.message = message ;
        this.status = status;
    }

     // exception with message

    public BlogAPIException(String message, HttpStatus status , String message1 ){
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus(){
        return status;
    }
    @Override
    public String getMessage(){
        return message;
    }


}
