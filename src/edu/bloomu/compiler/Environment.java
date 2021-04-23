package edu.bloomu.compiler;

import edu.bloomu.compiler.value.*;
import edu.bloomu.compiler.value.function.BuiltinFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * An environment simply is used to store variables that are being by used by instruction.
 *
 * @author Maxwell Norfolk
 */
public class Environment {
    
    private Environment parent;
    public Map<String, Value> params;
    private Map<String, Value> variables;
    
    public Environment(Environment parent) {
        this.parent = parent;
        variables = new HashMap<>();
        params = new HashMap<>();
    }

    public void setParams(Map<String, Datatype> vars) {
        params = new HashMap<>(vars.size());
        setVars(vars, params);
    }

    /**
     * Overwrites the current variables and replaces them with the following
     */
    public void setVariables(Map<String, Datatype> vars) {
        variables = new HashMap<>(vars.size());
        setVars(vars, variables);
    }

    private void setVars(Map<String, Datatype> datatypes, Map<String, Value> vars) {

        for (Map.Entry<String, Datatype> var : datatypes.entrySet()) {
            switch (var.getValue()) {
                case INT:
                    vars.put(var.getKey(), new Int());
                    break;
                case ARRAY:
                    vars.put(var.getKey(), new Array());
                    break;
                case FUNC:
                    vars.put(var.getKey(), new FunctionValue());
                    break;
            }
        }
    }

    public Value find(String key) {
        try {
            if (params.containsKey(key))
                return params.get(key);
            return variables.containsKey(key)
                    ? variables.get(key)  // if can't find in this scope, go up one
                    : parent.find(key);

        } catch (NullPointerException npe) { // cannot find
            throw new VariableNotDeclaredException("The variable: '"
                    + key + "' could not be found", npe);
        }
    }

    public void setParam(String key, Value value) {
        if (params.containsKey(key))
            params.put(key, value);
        else throw new IllegalArgumentException("Key '" + key + "' not a valid param!");
    }

    /**
     * Resets all variables in the environment
     */
    public Environment copy() {
        Environment copy = new Environment(parent);
        copy.params = params;
        for (Map.Entry<String, Value> entry : variables.entrySet()) {
            copy.variables.put(entry.getKey(), entry.getValue().copy());
        }
        return copy;
    }

    /**
     * An interface used to for functions that rely on and modify the environment
     */
    public interface EnvironmentFunctionCode extends BuiltinFunction.BuiltinCode {

        @Override
        void run(Value... values);
    }

    @Override
    public String toString() {
        return variables.toString();
    }
}
