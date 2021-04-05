package edu.bloomu.compiler.value;

import edu.bloomu.compiler.value.function.Function;

import java.util.Arrays;

/**
 * Represents an array
 *
 * @author Maxwell Norfolk
 */
public class Array implements Value {

    Object[] value;

    @Override
    public Datatype getType() {
        return Datatype.ARRAY;
    }

    @Override
    public int asInt() throws DataConversionException {
        try {
            if (value.length == 1)
                return (int) value[0];
        } catch (ClassCastException exc) {
            throw new DataConversionException("Cannot express '"
                    + value[0] + "' as int!", exc);
        }
        throw new DataConversionException("Cannot express array as an Int");
    }

    @Override
    public Object[] asArray() throws DataConversionException {
        return value;
    }

    @Override
    public Function asFunction() throws DataConversionException {
        throw new DataConversionException("Cannot express array as a function");

    }

    @Override
    public void set(Object newVal) {
        this.value = (Object[]) newVal;
    }

    @Override
    public String toString() {
        return Arrays.toString(value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
