package edu.bloomu.compiler.value;

import edu.bloomu.compiler.value.function.Function;

/**
 * A simple class used to represent a function that is treated as a value. This holds
 * a function object
 *
 * @author Maxwell Norfolk
 */
public class FunctionValue implements Value {

    private Function code;


    public FunctionValue() {
        this(null);
    }

    public FunctionValue(Function wrapped) {
        this.code = wrapped;
    }


    public void callOn(Value... params) {
        code.callOn(params);
    }

    @Override
    public void set(Object newVal) {
        set(newVal); // TODO FIX THIS
    }

    @Override
    public FunctionValue copy() {
        return new FunctionValue(code);
    }

    @Override
    public Datatype getType() {
        return Datatype.FUNC;
    }

    @Override
    public int asInt() throws DataConversionException {
        throw new DataConversionException("Cannot convert function to Int");
    }

    @Override
    public Function asFunction() throws DataConversionException {
        return code;
    }
}
