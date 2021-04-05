package edu.bloomu.compiler.value;


import edu.bloomu.compiler.value.function.Function;

/**
 * Represents a number
 *
 * @author Maxwell Norfolk
 */
public class Int implements Value {

    private int value;

    @Override
    public Datatype getType() {
        return Datatype.INT;
    }

    @Override
    public int asInt() {
        return value;
    }

    @Override
    public Object[] asArray() {
        return new Object[]{value};
    }

    @Override
    public Function asFunction() throws DataConversionException {
        throw new DataConversionException("Cannot convert Int '" + value + "' to Function");
    }

    @Override
    public void set(Object newVal) {
        this.value = (int) newVal;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
