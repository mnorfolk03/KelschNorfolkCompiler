package edu.bloomu.compiler.value.function;

import edu.bloomu.compiler.value.DataConversionException;
import edu.bloomu.compiler.value.Datatype;
import edu.bloomu.compiler.value.Value;

/**
 * A subroutine
 *
 * @author Maxwell Norfolk
 */
public abstract class Function implements Value {

    /**
     * Calls the function on the given parameters
     */
    public abstract void callOn(Value... params);

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
        return this;
    }

    @Override
    public Function copy() {
        return this; // TODO ???
    }

    @Override
    public void set(Object newVal) {
        // TODO think about this
    }
}
