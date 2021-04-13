package edu.bloomu.compiler;


import edu.bloomu.compiler.value.Array;
import edu.bloomu.compiler.value.Int;
import edu.bloomu.compiler.value.Value;
import edu.bloomu.compiler.value.function.Function;

/**
 * Represents a single instruction
 *
 * @author Maxwell Norfolk
 */
public class Instruction {

    /**
     * Creates a new instruction with the given params
     *
     * @param stuff an array of string of the form [FUNC_NAME, PARAM_1, PARAM_2, ..., PARAM_N]
     */
    public Instruction(String[] stuff) {
        params = new String[stuff.length - 1];
        System.arraycopy(stuff, 1, params, 0, stuff.length - 1);
        func = stuff[0];
    }

    private String func;
    private String[] params;

    public void call(Environment host) {
        Function func = host.find(this.func).asFunction();
        Value[] vParams = new Value[params.length];

        for (int i = 0; i < params.length; i++) {
            if (params[i].charAt(0) == '#')
                vParams[i] = new Int(Integer.parseInt(params[i].substring(1)));
            else if (params[i].charAt(0) == '"') {
                Value[] values = new Value[params[i].length() - 2];
                for (int j = 1; j < params[i].length() - 1; j++) {
                    values[j - 1] = new Int(params[i].charAt(j));
                }
                vParams[i] = new Array(values);
            } else
                vParams[i] = host.find(params[i]);
        }
        func.callOn(host, vParams);
    }
}
