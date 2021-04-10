package edu.bloomu.compiler.value;

import edu.bloomu.compiler.value.function.Function;

/**
 * A simple class used to represent a function that is treated as a value. This holds
 * a function object
 * <p>
 * There are 2 types of functions, nullfunc and func. A func once set can never change
 * a nullfunc has no value to start and must be set before being used. Calling reset will
 * not change a func, but will reset a nullfunc.
 *
 * @author Maxwell Norfolk
 */
public class FunctionValue implements Value {

    private Function value;
    private boolean resetable;

    public FunctionValue() {
        this(null);
        resetable = true;
    }

    public FunctionValue(Function wrapped) {
        this.value = wrapped;
        resetable = false;
    }


    public void callOn(Value... params) {
        value.callOn(params);
    }

    @Override
    public void set(Object newVal) {
        if (value != null)
            value = (Function) newVal;
        else
            throw new IllegalArgumentException("This function has already been set");
    }

    @Override
    public void set(Value newVal) throws DataConversionException {
        this.value = newVal.asFunction();
    }

    @Override
    public FunctionValue copy() {
        return new FunctionValue(value);
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
        return value;
    }

    @Override
    public void reset() {
        if (resetable)
            this.value = null;
    }
}
