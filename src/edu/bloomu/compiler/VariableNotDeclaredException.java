package edu.bloomu.compiler;

/**
 * This exception should be thrown when a variable has not been declared
 *
 * @author Maxwell Norfolk
 */
public class VariableNotDeclaredException extends RuntimeException {

    public VariableNotDeclaredException() {
    }

    public VariableNotDeclaredException(String message) {
        super(message);
    }

    public VariableNotDeclaredException(String message, Throwable cause) {
        super(message, cause);
    }

    public VariableNotDeclaredException(Throwable cause) {
        super(cause);
    }
}
