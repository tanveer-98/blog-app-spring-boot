package com.springboot.blog.exception;


import com.springboot.blog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


// @controllerAdvice used @Component internally
// so the class is autodetected by componentScan
/// Thus GlobalExceptionHandler can be used as Spring bean

// for customizing validation response we need to extend the class with ResponseEntityExceptionHandler
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    // handle specific exceptions as well as global exceptions


    // handles specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest
    ) {
        // webrequest provides a way to create and manage HTTP connections and send requests to a web serve
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(
            BlogAPIException exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //handles global exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // Custom Response for Error Validtion Reponse

    // Approach 1: by Extending Class


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // create a map and add validations for all the fields in the postDto
        Map<String, String> errors = new HashMap<>();
        // get all erros from exception e and put all those in the map object
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            // we donont get getField() method directly so need to cast object

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(fieldName, message);

        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);


    }


    // Approach 2 :
    // Note : Make sure to remove the extended class so as to not cause any functional ambiguity errors


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleMethodArgumentNotValidException(
//            MethodArgumentNotValidException exception, WebRequest webRequest
//    ) {// create a map and add validations for all the fields in the postDto
//        Map<String, String> errors = new HashMap<>();
//        // get all errros from exception e and put all those in the map object
//        exception.getBindingResult().getAllErrors().forEach((error) -> {
//
//            // we donont get getField() method directly so need to cast object
//
//            String fieldName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//
//            errors.put(fieldName, message);
//
//        });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//
//    }

    // handles exception when unauthorized access is generated
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(
            AccessDeniedException exception, WebRequest webRequest
    ) {
        // webrequest provides a way to create and manage HTTP connections and send requests to a web serve
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.UNAUTHORIZED);

    }

}
