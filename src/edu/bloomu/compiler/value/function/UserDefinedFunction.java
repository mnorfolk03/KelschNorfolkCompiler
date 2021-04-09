package edu.bloomu.compiler.value.function;

import edu.bloomu.compiler.Instruction;
import edu.bloomu.compiler.value.Value;

import java.util.List;

/**
 * A function defined by the user
 *
 * @author Maxwell Norfolk
 */
public class UserDefinedFunction extends Function {

    private List<Instruction> body;

    @Override
    public void callOn(Value... params) {
        for (Instruction instruction : body) {
            instruction.call();
        }
    }
}
