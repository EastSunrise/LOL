package wsg.lol.common.base;

import org.slf4j.Logger;

/**
 * Specified {@link RuntimeException} for the application.
 *
 * @author Kingen
 */
public class AppException extends RuntimeException {

    /**
     * @see wsg.lol.common.constant.ErrorCodeConst
     */
    private String errorCode;

    /**
     * Arguments for the error message.
     */
    private String[] args;

    public AppException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public AppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(String errorCode, String message, String[] args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
    }

    public AppException(String errorCode, String[] args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public AppException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    public AppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String[] getArgs() {
        return args;
    }

    /**
     * {@inheritDoc}
     */
    public Throwable fillInStackTrace() {
        return this;
    }

    public AppException error(Logger logger) {
        logger.error(this.getMessage());
        return this;
    }
}
