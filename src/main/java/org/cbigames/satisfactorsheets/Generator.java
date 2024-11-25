package org.cbigames.satisfactorsheets;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

    /**Generate an Excel spreadsheet (.xlsx) for the given recipe
     * @param recipeToMake the recipe to make the sheet for
     * @param out the output stream to write the sheet to. NOTE: the stream will NOT be closed
     */
    public static void generate(Recipe recipeToMake, OutputStream out){
        try (Workbook wb = new XSSFWorkbook()){
            Sheet sheet = wb.createSheet("test");
            Row[] rows = new Row[8];
            for(int i=0;i< rows.length;i++){
                rows[i] = sheet.createRow(i);
            }
            ArrayList<SheetRecipe> sheetRecipes = new ArrayList<>();
            ArrayList<String> rawResources = new ArrayList<>();
            sheetRecipes.add(new SheetRecipe(recipeToMake,rows,0,1));

            for(int i=0;i<sheetRecipes.size();i++){
                SheetRecipe cr = sheetRecipes.get(i);
                for(int j=0;j<cr.getRecipe().getNumInputs();j++){
                    String item = cr.getRecipe().getInput(j);
                    //if the item has not already been added to the sheet
                    if(!resourceInSheet(item,sheetRecipes)){
                        boolean success = false;
                        //look for the item
                        //first look in the selected alt recipes
                        //TODO alt recipes

                        //check the default recipes
                        for(Recipe r: recipes){
                            if(r.getOutputItem().equals(item)){
                                success=true;
                                sheetRecipes.add(new SheetRecipe(r,rows,sheetRecipes.size(),0));
                                break;
                            }
                        }
                        //if not found then add to raw resources list
                        if(!success){
                            if(!rawResources.contains(item))
                                rawResources.add(item);
                        }
                    }
                }
            }

            //connect the recipes together

            //go through all the sheet recipes
            for(SheetRecipe sr: sheetRecipes) {
                String outItem = sr.getRecipe().getOutputItem();
                //for each sheet recipe, go through all the inputs finding all the recipes that require it
                for(SheetRecipe osr:sheetRecipes){
                    if(sr == osr)
                        continue;

                    for(int j=0;j<osr.getRecipe().getNumInputs();j++){
                        if(osr.getRecipe().getInput(j).equals(outItem)){
                            sr.addRequires(osr,j);
                        }
                    }
                    //then go through all the second outputs, fining all other things that output it
                    if(osr.getRecipe().isHasSecondOutput() && osr.getRecipe().getSecondOutputItem().equals(outItem)){
                        sr.addReduction(osr);
                    }

                }

            }
            for(SheetRecipe sr:sheetRecipes){
                sr.writeMachineCell();
            }

            Row preRawResourcesRow =  sheet.createRow(10);
            Cell rawResourceDescriptionCell = preRawResourcesRow.createCell(1);
            rawResourceDescriptionCell.setCellValue("Raw Resource");
            Cell rawResourceammountCell = preRawResourcesRow.createCell(2);
            rawResourceammountCell.setCellValue("Total");

            //display the raw resources
            for(int i=0;i< rawResources.size();i++){
                Row row = sheet.createRow(i+11);
                Cell nameCell = row.createCell(1);
                String item = rawResources.get(i);
                nameCell.setCellValue(item);
                ArrayList<String> resourceRequireCells =new ArrayList<>();
                ArrayList<String> reductionCells = new ArrayList<>();
                for(SheetRecipe osr:sheetRecipes) {
                    for (int j = 0; j < osr.getRecipe().getNumInputs(); j++) {
                        if (osr.getRecipe().getInput(j).equals(item)) {
                            resourceRequireCells.add(osr.getInputTotal(j).getAddress().formatAsString());
                        }
                    }
                    if (osr.getRecipe().isHasSecondOutput() && osr.getRecipe().getSecondOutputItem().equals(item)) {
                        reductionCells.add(osr.getSecondaryOutputTotal().getAddress().formatAsString());
                    }
                }
                Cell ammountCell = row.createCell(2);
                if(reductionCells.isEmpty()){
                    ammountCell.setCellFormula(String.join("+",resourceRequireCells.toArray(new String[]{})));
                }else {
                    ammountCell.setCellFormula(String.join("+",resourceRequireCells.toArray(new String[]{}))+"-"+String.join("-",reductionCells.toArray(new String[]{})));
                }
            }


            //save the file
            wb.write(out);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean resourceInSheet(String resource, ArrayList<SheetRecipe> sheet){
        for (SheetRecipe sheetRecipe : sheet) {
            if (sheetRecipe.getRecipe().getOutputItem().equals(resource)) {
                return true;
            }
        }
        return false;
    }

}
