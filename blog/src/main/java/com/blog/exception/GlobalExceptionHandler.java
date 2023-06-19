package com.blog.exception;
import com.blog.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

//If u now handle the Exception, whatever class you are creating,
            //now that class should Extend ResponseEntityException Handler class.
    @ControllerAdvice          // Acts Like a Catch Block.--------------------------------------------------------
                      //Receives an Exception occurred anywhere in the project.-----------------------------------

    public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(ResourceNotFoundException.class)  //It'll handle specific exceptions -------------------
        public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                              WebRequest webRequest) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                    webRequest.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
        @ExceptionHandler(Exception.class)   // global exceptions (It'll handle all the exceptions)---------------
        public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                                  WebRequest webRequest){
            ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                    webRequest.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

