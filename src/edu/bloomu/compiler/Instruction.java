package edu.bloomu.compiler;


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
    public Instruction(Environment env, String[] stuff) {
        host = env;
        params = new String[stuff.length - 1];
        System.arraycopy(stuff, 1, params, 0, stuff.length - 1);
        func = stuff[0];
    }

    private String func;
    private Environment host;
    private String[] params;

    public void call() {
        Function func = host.find(this.func).asFunction();
        Value[] vParams = new Value[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i].charAt(0) == '#')
                vParams[i] = new Int(Integer.parseInt(params[i].substring(1)));
            else
                vParams[i] = host.find(params[i]);
        }

        func.callOn(vParams);
    }
}
