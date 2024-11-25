package org.cbigames;

import org.cbigames.satisfactorsheets.Generator;
import org.cbigames.satisfactorsheets.Recipe;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) {
        Recipe r = null;
        for(int i=0;i<Generator.recipes.length;i++){
            if(Generator.recipes[i].getOutputItem().equals("FusedModularFrame")){
                r = Generator.recipes[i];
                break;
            }
        }

        try {
            assert r != null;
            Generator.generate(r,new FileOutputStream(r.getOutputItem()+".xlsx"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done");
    }
}