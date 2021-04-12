package edu.bloomu.compiler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
        JFileChooser chooser = new JFileChooser();
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

            // read each line of the file as one instruction
            while (input.hasNextLine()) {
                ArrayList<String> tokens = new ArrayList<>();
                String instruction = input.nextLine();

                Scanner line = new Scanner(instruction);

                // tokenize line, delimiting by whitespace
                while (line.hasNext()) {
                    String token = line.next();
                    tokens.add(token);
                }

                String[] test = tokens.toArray(new String[0]);
                program.add(test);
            }

            // for testing
            for (int i = 0; i < program.size(); i++) {
                String[] instructionLine = program.get(i);
                for (int j = 0; j < instructionLine.length; j++) {
                    System.out.print("\"" + instructionLine[j] + "\" ");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
