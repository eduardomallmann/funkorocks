package com.mcfadyen.shoppingcart.backend.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Class responsible for the ErrorMessages in the business exceptions.
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = -5469763061793409313L;

    private HttpStatus status;
    private String message;
    private List<String> errors;

    /**
     * Main empty constructor.
     */
    public ErrorMessage() {
    }

    /**
     * Optional constructor with complete attributes.
     *
     * @param status  status to be sent in the HttpResponse
     * @param message developers message about the exception
     * @param errors  errors list informed by the system
     */
    public ErrorMessage(final HttpStatus status,
                        final String message,
                        final List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
