package edu.bloomu.compiler.value.function;

import edu.bloomu.compiler.Environment;
import edu.bloomu.compiler.value.Value;

/**
 * A subroutine
 *
 * @author Maxwell Norfolk
 */
public abstract class Function {

    /**
     * Calls the function on the given parameters
     */
    public abstract void callOn(Environment env, Value... params);

}
