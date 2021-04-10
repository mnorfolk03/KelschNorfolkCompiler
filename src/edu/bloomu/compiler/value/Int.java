package edu.bloomu.compiler.value;


import edu.bloomu.compiler.value.function.Function;

/**
 * Represents a number
 *
 * @author Maxwell Norfolk
 */
public class Int implements Value {

    public static final int DEFAULT_VALUE = 0;

    private int value;

    public Int() {
        this.value = DEFAULT_VALUE;
    }

    public Int(int value) {
        this.value = value;
    }

    @Override
    public Datatype getType() {
        return Datatype.INT;
    }

    @Override
    public int asInt() {
        return value;
    }

    @Override
    public Function asFunction() throws DataConversionException {
        throw new DataConversionException("Cannot convert Int '" + value + "' to Function");
    }

    @Override
    public Int copy() {
        return new Int(this.value);
    }

    @Override
    public void set(Object newVal) {
        this.value = (int) newVal;
    }

    @Override
    public void set(Value newVal) throws DataConversionException {
        this.value = newVal.asInt();
    }

    @Override
    public void reset() {
        this.value = DEFAULT_VALUE;
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
