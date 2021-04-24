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
 * tokenized and executed.
 *
 * @author Schyler Kelsch
 * @author Maxwell Norfolk
 */
public class Compiler {
    public static void main(String[] args) {
        // display file chooser and get selected file
        JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt");
        chooser.setFileFilter(filter);
        File file;
        int buttonClicked = chooser.showOpenDialog(null);
        if (buttonClicked == JFileChooser.CANCEL_OPTION) { // user clicks cancel
            System.exit(0);
        }
        file = chooser.getSelectedFile();

        // scan input file and run program
        try {
            ArrayList<String[]> program = new ArrayList<>();
            Scanner input = new Scanner(file);

            // read each line of the file
            while (input.hasNextLine()) {
                ArrayList<String> tokens = new ArrayList<>();
                String instruction = input.nextLine();
                
                // blank line or comment... no need to go further
                if (instruction.isEmpty() || instruction.startsWith("//")) {
                    continue;
                }

                // tokenize line
                Matcher line = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(instruction);
                while (line.find()) {
                    String token = line.group(1);
                    if (!token.equals(" ")) {
                        tokens.add(token.replaceAll("\\\\n", "\n"));
                    }
                }
                program.add(tokens.toArray(new String[0]));
            }

            // set up execution environment
            program.add(0, new String[0]); // no params
            Environment builtin = new Environment(null);
            HostEnvironmentSetup.setup(builtin);

            // execute program
            UserDefinedFunction main = UserDefinedFunction.parse(program);
            main.callOn(builtin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
