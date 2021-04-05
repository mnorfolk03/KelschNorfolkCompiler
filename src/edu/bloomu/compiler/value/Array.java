package edu.bloomu.compiler.value;

import edu.bloomu.compiler.value.function.Function;

import java.util.Arrays;

/**
 * Represents an array
 *
 * @author Maxwell Norfolk
 */
public class Array implements Value {

    Value[] value;

    public Array() {
        this(new Value[0]);
    }

    public Array(Value[] value) {
        this.value = value;
    }

    @Override
    public Datatype getType() {
        return Datatype.ARRAY;
    }

    @Override
    public int asInt() throws DataConversionException {
        try {
            if (value.length == 1)
                return value[0].asInt();
        } catch (ClassCastException exc) {
            throw new DataConversionException("Cannot express '"
                    + value[0] + "' as int!", exc);
        }
        throw new DataConversionException("Cannot express array as an Int");
    }

    @Override
    public Value[] asArray() throws DataConversionException {
        return value;
    }

    @Override
    public Function asFunction() throws DataConversionException {
        throw new DataConversionException("Cannot express array as a function");

    }

    @Override
    public void set(Object newVal) {
        this.value = (Value[]) newVal;
    }

    @Override
    public Array copy() {
        Value[] arr2 = new Value[size()];
        for (int i = 0; i < value.length; i++) {
            arr2[i] = value[i].copy();
        }
        return new Array(arr2);
    }

    /**
     * Returns the number of elements in the array
     */
    public int size() {
        return value.length;
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
