package edu.bloomu.compiler.value.function;

import edu.bloomu.compiler.Environment;
import edu.bloomu.compiler.Instruction;
import edu.bloomu.compiler.SyntaxException;
import edu.bloomu.compiler.value.Datatype;
import edu.bloomu.compiler.value.Value;

import java.util.*;

/**
 * A function defined by the user
 *
 * @author Maxwell Norfolk
 */
public class UserDefinedFunction extends Function {

    private Environment host;
    private List<Instruction> body;
    private String[] paramNames;

    public static UserDefinedFunction parse(Environment parent, List<String[]> arr) {

        Environment host = new Environment(parent);
        Map<String, Datatype> vars = new HashMap<>();
        Iterator<String[]> it = arr.iterator();

        List<String> params = new ArrayList<>();

        // extract the parameters and store them
        String[] a = it.next();
        for (int i = 1; i < a.length - 1; ) {
            String varName = a[++i];// start at index 2

            // datatype
            String dataName = a[++i].toUpperCase();
            Datatype type = dataName.equals("NULLFUNC")
                    ? Datatype.FUNC
                    : Datatype.valueOf(dataName);

            vars.put(varName, type);
            params.add(varName);
        }


        Map<String, Function> innerFuncs = new HashMap<>();

        List<Instruction> funcBody = new ArrayList<>();

        while (it.hasNext()) {
            String[] line = it.next();

            // is the line a variable declaration?
            Datatype datatype = null;
            switch (line[0]) {
                case "int":
                    datatype = Datatype.INT;
                    break;
                case "array":
                    datatype = Datatype.ARRAY;
                    break;
                case "nullfunc":
                    datatype = Datatype.FUNC;
                    break;
            }


            if (datatype != null) {
                for (int i = 1; i < line.length; i++) {
                    vars.put(line[i], datatype);
                }
                continue;
            }

            // represents an inner function

            if (line[0].equals("func")) {
                int endCounter = 1;
                try {
                    List<String[]> innerFunctionLines = new ArrayList<>();
                    innerFunctionLines.add(line);
                    while (endCounter > 0) {
                        String[] innerLine = it.next();
                        if (innerLine[0].equals("func"))
                            endCounter++;
                        else if (innerLine[0].equals("end"))
                            endCounter--;
                    }

                    // save inner function into the maps
                    innerFuncs.put(line[1],
                            UserDefinedFunction.parse(host, innerFunctionLines));
                    vars.put(line[1], Datatype.FUNC);

                } catch (NoSuchElementException nse) { // syntax error
                    throw new SyntaxException("Expected 'end', but could not find. Expected '"
                            + endCounter + "' more 'end's", nse);
                }
            } else { // ELSE it is a regular function
                funcBody.add(new Instruction(host, line));
            }
        }

        // set the variables of the environment to the following
        host.setVariables(vars);

        // define the variables of inner functions
        for (Map.Entry<String, Function> entry : innerFuncs.entrySet()) {
            host.find(entry.getKey()).set(entry.getValue());
        }

        return new UserDefinedFunction(host, funcBody, params.toArray(new String[]{}));
    }

    /**
     * Do not use this to create user defined functions,
     * instead use {@link #parse(Environment, List)} to create them
     */
    private UserDefinedFunction(Environment host, List<Instruction> body, String[] params) {
        this.host = host;
        this.body = body;
        this.paramNames = params;
    }

    @Override
    public void callOn(Value... params) {
        for (int i = 0; i < paramNames.length; i++) {
            host.find(paramNames[i]).set(params[i]);
        }

        for (Instruction instruction : body) {
            instruction.call();
        }
        host.reset();
    }
}
