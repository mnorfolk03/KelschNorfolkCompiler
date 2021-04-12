package edu.bloomu.compiler.value.function;

import edu.bloomu.compiler.Environment;
import edu.bloomu.compiler.value.Datatype;
import edu.bloomu.compiler.value.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * A small class used to setup environments
 *
 * @author Maxwell Norfolk
 */
public class HostEnvironmentSetup {


    public static void setup(Environment builtin) {

        // p0 = p1+p2 ; add p0 p1 p2
        BuiltinFunction add = new BuiltinFunction((p) -> {
            p[0].set(p[1].asInt() + p[2].asInt());
        });

        BuiltinFunction sub = new BuiltinFunction((p) -> {
            p[0].set(p[1].asInt() - p[2].asInt());
        });

        BuiltinFunction mul = new BuiltinFunction((p) -> {
            p[0].set(p[1].asInt() * p[2].asInt());
        });

        BuiltinFunction div = new BuiltinFunction((p) -> {
            p[0].set(p[1].asInt() / p[2].asInt());
        });

        BuiltinFunction mod = new BuiltinFunction((p) -> {
            p[0].set(p[1].asInt() % p[2].asInt());
        });

        // booleans
        // if 0 then false
        // else true
        BuiltinFunction or = new BuiltinFunction((p) -> {
            p[0].set((p[1].asInt() | p[2].asInt()) != 0 ? 1 : 0);
        });

        BuiltinFunction and = new BuiltinFunction((p) -> {
            p[0].set((p[1].asInt() != 0) && (p[2].asInt() != 0) ? 1 : 0);
        });

        BuiltinFunction not = new BuiltinFunction((p) -> {
            p[0].set(p[1].asInt() == 0 ? 1 : 0);
        });

        BuiltinFunction print = new BuiltinFunction((p) -> {
            System.out.print(p[0]);
        });

        BuiltinFunction prints = new BuiltinFunction((p) -> {

            for (Value i : p[0].asArray()) {
                System.out.print((char) i.asInt());
            }
        });

        BuiltinFunction length = new BuiltinFunction((p) -> {
            p[0].set(p[1].asArray().length);
        });

        // variable stuff
        BuiltinFunction copy = new BuiltinFunction((p) -> {
            p[0].set(p[1].copy());
        });

        BuiltinFunction point = new BuiltinFunction((p) -> {
            p[0].set(p[1]);
        });

        BuiltinFunction newSize = new BuiltinFunction((p) -> {
            int size = p[1].asInt();
            Value[] newArr = new Value[size];
            p[0].set(newArr);
            for (int i = 0; i < size; i++) {
                newArr[i] = p[2].copy();
            }
        });

        BuiltinFunction fill = new BuiltinFunction((p) -> {
            Value[] arr = p[0].asArray();
            for (int i = 1; i < p.length; i++) {
                arr[i - 1].set(p[i]);
            }
        });

        BuiltinFunction at = new BuiltinFunction((p) -> {
            p[0].set(p[1].asArray()[p[2].asInt()]);
        });

        BuiltinFunction put = new BuiltinFunction((p) -> {
            p[1].asArray()[p[2].asInt()].set(p[0]);
        });

        BuiltinFunction call = new BuiltinFunction((p) -> {
            Value[] params = new Value[p.length - 1];
            System.arraycopy(p, 1, params, 0, p.length - 1);
            p[0].asFunction().callOn(params);
        });

        Map<String, BuiltinFunction> funcs = new HashMap<>();
        funcs.put("add", add);
        funcs.put("sub", sub);
        funcs.put("mul", mul);
        funcs.put("div", div);
        funcs.put("mod", mod);
        funcs.put("or", or);
        funcs.put("and", and);
        funcs.put("not", not);
        funcs.put("print", print);
        funcs.put("prints", prints);
        funcs.put("length", length);
        funcs.put("copy", copy);
        funcs.put("point", point);
        funcs.put("newSize", newSize);
        funcs.put("fill", fill);
        funcs.put("at", at);
        funcs.put("put", put);
        funcs.put("call", call);


        Map<String, Datatype> datatypes = new HashMap<>();
        for (String name : funcs.keySet()) {
            datatypes.put(name, Datatype.FUNC);
        }
        builtin.setVariables(datatypes);
        for (Map.Entry<String, BuiltinFunction> entry : funcs.entrySet()) {
            builtin.find(entry.getKey()).set(entry.getValue());
        }
    }
}
