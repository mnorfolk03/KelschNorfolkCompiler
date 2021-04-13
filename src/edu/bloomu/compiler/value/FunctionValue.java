package edu.bloomu.compiler.value;

import edu.bloomu.compiler.value.function.Function;

/**
 * A simple class used to represent a function that is treated as a value. This holds
 * a function object
 * <p>
 * There are 2 types of functions, nullfunc and func. The only difference is func must
 * have it's body declared, while nullfunc is treated as a function pointer
 *
 * @author Maxwell Norfolk
 */
public class FunctionValue implements Value {

    private Function value;

    public FunctionValue() {
        this(null);
    }

    public FunctionValue(Function wrapped) {
        this.value = wrapped;
    }

    @Override
    public void set(Object newVal) {
        if (value == null)
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
}
