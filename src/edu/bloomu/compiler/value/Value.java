package edu.bloomu.compiler.value;


import edu.bloomu.compiler.value.function.Function;

/**
 * Represents a value either an int, array or function
 *
 * @author Maxwell Norfolk
 */
public interface Value {

    Datatype getType();

    /**
     * Converts the object to an int or throws an exception if the object
     * cannot be converted to an int
     */
    int asInt() throws DataConversionException;

    /**
     * Converts the object to an array, or throws an exception if the object cannot
     * be converted to an array
     */
    default Value[] asArray() throws DataConversionException {
        return new Value[]{this};
    }


    /**
     * Returns the object as a function, or throws an exception if the object cannot be
     * converted to a function
     */
    Function asFunction() throws DataConversionException;

    /**
     * Sets the value to a different value
     */
    void set(Object newVal);

    /**
     * Sets the value to a different value
     */
    void set(Value newVal) throws DataConversionException;

    /**
     * Creates a copy of the value
     */
    Value copy();

}
