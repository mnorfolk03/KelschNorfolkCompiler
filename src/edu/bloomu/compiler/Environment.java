package edu.bloomu.compiler;

import edu.bloomu.compiler.value.Array;
import edu.bloomu.compiler.value.Datatype;
import edu.bloomu.compiler.value.Int;
import edu.bloomu.compiler.value.Value;
import edu.bloomu.compiler.value.FunctionValue;

import java.util.HashMap;
import java.util.Map;

/**
 * An environment simply is used to store variables that are being by used by instruction.
 *
 * @author Maxwell Norfolk
 */
public class Environment {

    public Environment(Environment parent, Map<String, Datatype> vars) {
        this.parent = parent;
        variables = new HashMap<>(vars.size());
        for (Map.Entry<String, Datatype> var : vars.entrySet()) {
            switch (var.getValue()) {
                case INT:
                    variables.put(var.getKey(), new Int());
                    break;
                case ARRAY:
                    variables.put(var.getKey(), new Array());
                    break;
                case FUNC:
                    variables.put(var.getKey(), new FunctionValue());
                    break;
            }
        }
    }

    private Environment parent;
    private Map<String, Value> variables;

    public Value find(String key) {
        try {
            return variables.containsKey(key)
                    ? parent.find(key)  // if can't find in this scope, go up one
                    : variables.get(key);

        } catch (NullPointerException npe) { // cannot find
            throw new VariableNotDeclaredException("The variable: '"
                    + key + "' could be found", npe);
        }
    }
}
