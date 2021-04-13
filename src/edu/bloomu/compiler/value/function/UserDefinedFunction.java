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

    private List<Instruction> body;
    private String[] paramNames;
    private Map<String, Datatype> paramsMap;
    private Map<String, Datatype> varsMap;
    private Map<String, Function> innerFuncs;

    public static UserDefinedFunction parse(List<String[]> arr) {

        Map<String, Datatype> varsMap = new HashMap<>();
        Map<String, Datatype> paramsMap = new HashMap<>();
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

            paramsMap.put(varName, type);
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
                    varsMap.put(line[i], datatype);
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
                        innerFunctionLines.add(innerLine);
                    }

                    innerFunctionLines.remove(innerFunctionLines.size() - 1);


                    // save inner function into the maps
                    innerFuncs.put(line[1],
                            UserDefinedFunction.parse(innerFunctionLines));
                    varsMap.put(line[1], Datatype.FUNC);

                } catch (NoSuchElementException nse) { // syntax error
                    throw new SyntaxException("Expected 'end', but could not find. Expected '"
                            + endCounter + "' more 'end's", nse);
                }
            } else { // ELSE it is a regular function
                funcBody.add(new Instruction(line));
            }
        }

        return new UserDefinedFunction(funcBody, params.toArray(new String[]{}),
                paramsMap, varsMap, innerFuncs);
    }

    /**
     * Do not use this to create user defined functions,
     * instead use {@link #parse(List)} to create them
     */
    private UserDefinedFunction(List<Instruction> body, String[] paramNames,
                                Map<String, Datatype> params, Map<String, Datatype> vars,
                                Map<String, Function> innerFuncs) {
        this.body = body;
        this.paramNames = paramNames;
        this.paramsMap = params;
        this.varsMap = vars;
        this.innerFuncs = innerFuncs;
    }

    @Override
    public void callOn(Environment host, Value... params) {
        Environment newHost = new Environment(host);
        newHost.setParams(paramsMap);
        newHost.setVariables(varsMap);

        // setup functions
        for (Map.Entry<String, Function> entry : innerFuncs.entrySet()) {
            newHost.find(entry.getKey()).set(entry.getValue());
        }

        // setup params
        for (int i = 0; i < paramNames.length; i++) {
            newHost.setParam(paramNames[i], params[i]);
        }

        // execute instructions
        for (Instruction instruction : body) {
            instruction.call(newHost);
        }
    }
}
