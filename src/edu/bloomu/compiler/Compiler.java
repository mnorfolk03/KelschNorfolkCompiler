package edu.bloomu.compiler;

import edu.bloomu.compiler.value.function.HostEnvironmentSetup;
import edu.bloomu.compiler.value.function.UserDefinedFunction;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Prompts the user with a file navigator window. The user should find a text file
 * containing code for a program in this language. The program in this file will be
 * tokenized.
 *
 * @author Schyler Kelsch
 * @author Maxwell Norfolk
 */
public class Compiler {
    public static void main(String[] args) {
        // display file chooser and get selected file
        JFileChooser chooser = new JFileChooser("F:IntelliJProjects/KelschNorfolkCompiler/resources");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt");
        chooser.setFileFilter(filter);
        File file;
        int buttonClicked = chooser.showOpenDialog(null);
        if (buttonClicked == JFileChooser.CANCEL_OPTION) { // user clicks cancel
            System.exit(0);
        }
        file = chooser.getSelectedFile();

        // scan input file
        try {
            ArrayList<String[]> program = new ArrayList<>();
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                ArrayList<String> tokens = new ArrayList<>();
                String instruction = input.nextLine();
                if (instruction.isEmpty() || instruction.startsWith("//"))
                    continue;

                Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(instruction);
                while (m.find()) {
                    String s = m.group(1);
                    if (!s.equals(" "))
                        tokens.add(s);
                }
                String[] test = tokens.toArray(new String[0]);
                program.add(test);
            }

            program.add(0, new String[0]); // no params
            Environment builtin = new Environment(null);

            HostEnvironmentSetup.setup(builtin);

            UserDefinedFunction main = UserDefinedFunction.parse(program);
            main.callOn(builtin);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
