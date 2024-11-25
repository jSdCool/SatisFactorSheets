package org.cbigames;

import org.cbigames.satisfactorsheets.Generator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) {
        try {
            Generator.generate(Generator.recipes[3],new FileOutputStream("workbook.xlsx"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done");
    }
}