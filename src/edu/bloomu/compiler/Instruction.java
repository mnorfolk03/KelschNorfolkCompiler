package edu.bloomu.compiler;


import edu.bloomu.compiler.value.Value;
import edu.bloomu.compiler.value.function.Function;

/**
 * Represents a single instruction
 *
 * @author Maxwell Norfolk
 */
public class Instruction {

    private Function func;
    private Environment host;
    private String[] params;

    public void call() {
        Value[] vParams = new Value[params.length];
        for (int i = 0; i < params.length; i++) {
            vParams[i] = host.find(params[i]);
        }

        func.callOn(vParams);
    }
}
