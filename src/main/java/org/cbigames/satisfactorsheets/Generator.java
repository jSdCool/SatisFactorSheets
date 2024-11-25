package org.cbigames.satisfactorsheets;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Generator {
    public static final Recipe[] recipes, altRecipes = new Recipe[0];

    static {
        JSONArray recipesJSON;
        try {
            recipesJSON = Util.loadJSONArray(Objects.requireNonNull(Generator.class.getClassLoader().getResourceAsStream("recipes.json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recipes = new Recipe[recipesJSON.length()];
        for(int i=0;i<recipes.length;i++){
            recipes[i] = new Recipe(recipesJSON.getJSONObject(i));
        }
    }

    public static void generate(){
        try (Workbook wb = new XSSFWorkbook()){
            Sheet sheet = wb.createSheet("test");
            Row[] rows = new Row[8];
            for(int i=0;i< rows.length;i++){
                rows[i] = sheet.createRow(i);
            }

            SheetRecipe sr = new SheetRecipe(recipes[0],rows,0);
            for(int i=1;i<6;i++){
                new SheetRecipe(recipes[i],rows,i);
            }

            try (FileOutputStream fileOut = new FileOutputStream("workbook.xls")) {
                wb.write(fileOut);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
