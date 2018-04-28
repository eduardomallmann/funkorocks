package com.mcfadyen.shoppingcart.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class responsible for filtering the errors and response them in a global standard to the origin.
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the Business exceptions response to the origin.
     *
     * @param ex      business exception thrown
     * @param request requset made
     * @return the error message inside a response entity.
     */
    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<ErrorMessage> handleBusinessConflict(final BusinessException ex,
                                                                  final WebRequest request) {
        return new ResponseEntity<>(ex.getErrorMessage(), ex.getErrorMessage().getStatus());
    }

    /**
     * Handles the global exceptions.
     *
     * @param ex      exception thrown
     * @param request request made
     * @return the exceptions in a error message standard inside a response entity
     */
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorMessage> handleExceptions(final Exception ex,
                                                            final WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        List<String> errors = new ArrayList<>();
        Arrays.asList(ex.getStackTrace()).forEach(error -> errors.add(error.toString()));
        errorMessage.setErrors(errors);
        return new ResponseEntity<>(errorMessage, errorMessage.getStatus());
    }
}
