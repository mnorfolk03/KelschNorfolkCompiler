package edu.bloomu.compiler.value;

/**
 * Represents an error occurring while converting datatypes.
 *
 * @author Maxwell Norfolk
 */
public class DataConversionException extends RuntimeException {

    public DataConversionException(String message) {
        super(message);
    }

    public DataConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataConversionException(Throwable cause) {
        super(cause);
    }
}
