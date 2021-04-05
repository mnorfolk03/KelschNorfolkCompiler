package edu.bloomu.compiler.value.function;

/**
 * @author Maxwell Norfolk
 */
public class FunctionExample {

    public static void main(String[] args) {

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
            Integer[] arr = (Integer[]) p[0].asArray();
            for (int i : arr) {
                System.out.print((char) i);
            }
        });
    }
}
