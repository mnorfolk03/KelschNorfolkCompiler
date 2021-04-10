package edu.bloomu.compiler;

/**
 * Represents a Syntax Exception
 *
 * @author Maxwell Norfolk
 */
public class SyntaxException extends RuntimeException {
    public SyntaxException() {
    }

    public SyntaxException(String message) {
        super(message);
    }

    public SyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public SyntaxException(Throwable cause) {
        super(cause);
    }
}
