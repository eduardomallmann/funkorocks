package com.mcfadyen.shoppingcart.backend.exception;

/**
 * Class responsible for handle the exceptions thrown when a business exception occur.
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 1873357257094231501L;

    private ErrorMessage errorMessage;

    /**
     * Main constructor with one param.
     *
     * @param errorMessage error message informed
     */
    public BusinessException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    /**
     * Optional constuctor with the main param plus throwable cause.
     *
     * @param errorMessage error message informaed
     * @param cause        throwable cause
     */
    public BusinessException(final ErrorMessage errorMessage, final Throwable cause) {
        super(errorMessage.getMessage(), cause);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
