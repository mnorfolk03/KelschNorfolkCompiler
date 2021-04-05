package edu.bloomu.compiler.value.function;

import edu.bloomu.compiler.value.Value;

/**
 * A class representing a builtin function. These are functions that are not defined
 * by the user
 *
 * @author Maxwell Norfolk
 */
public class BuiltinFunction extends Function {

    private BuiltinCode code;

    /**
     * Initializes a new builtin function that uses the given code
     */
    public BuiltinFunction(BuiltinCode code) {
        this.code = code;
    }

    @Override
    public void callOn(Value... params) {
        code.run(params);
    }

    /**
     * An interface used to provide code to the function class
     */
    interface BuiltinCode {

        /**
         * The code defined by the user
         */
        void run(Value... values);
    }
}
