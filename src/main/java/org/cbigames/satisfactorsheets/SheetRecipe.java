package org.cbigames.satisfactorsheets;

import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;

public class SheetRecipe {
    public SheetRecipe(Recipe recipe, Row[] rows, int offset){
        this.recipe = recipe;

        //top row cells
        Cell c1 = rows[0].createCell(offset*with);
        Util.setBorders(c1,true,false,true,false);
        Cell c2 = rows[0].createCell(offset*with+1);
        c2.setCellValue("Per Min");
        Util.setBorders(c2,true,false,false,false);
        Cell c3 = rows[0].createCell(offset*with+2);
        c3.setCellValue("Per Craft");
        Util.setBorders(c3,true,false,false,false);
        Cell c4 = rows[0].createCell(offset*with+3);
        c4.setCellValue("Total");
        Util.setBorders(c4,true,false,false,false);

        Cell c5a = rows[1].createCell(offset*with);
        Util.setBorders(c5a,false,false,true,false);

        Cell c5 = rows[2].createCell(offset*with);
        Util.setBorders(c5,false,false,true,false);
        if(recipe.isHasSecondOutput()){
            c5.setCellValue(recipe.getSecondOutputItem());
        }

        Cell c6 = rows[3].createCell(offset*with);
        Util.setBorders(c6,false,true,true,false);
        c6.setCellValue(recipe.getOutputItem());

        Cell c7 = rows[4].createCell(offset*with);
        Util.setBorders(c7,false,false,true,false);
        c7.setCellValue(recipe.getInput0Item());

        Cell c8 = rows[5].createCell(offset*with);
        Util.setBorders(c8,false,false,true,false);
        if(recipe.getNumInputs()>=2) {
            c8.setCellValue(recipe.getInput1Item());
        }

        Cell c9 = rows[6].createCell(offset*with);
        Util.setBorders(c9,false,false,true,false);
        if(recipe.getNumInputs()>=3) {
            c9.setCellValue(recipe.getInput2Item());
        }

        Cell c10 = rows[7].createCell(offset*with);
        Util.setBorders(c10,false,true,true,false);
        if(recipe.getNumInputs()>=4) {
            c10.setCellValue(recipe.getInput3Item());
        }

        numberMachines = rows[1].createCell(offset*with+1);

        numberMachines.getSheet().addMergedRegion(new CellRangeAddress(numberMachines.getRowIndex(),numberMachines.getRowIndex(),numberMachines.getColumnIndex(),numberMachines.getColumnIndex()+2));

        output2PerMin = rows[2].createCell(offset*with+1);
        output2PerCraft = rows[2].createCell(offset*with+2);
        output2Total = rows[2].createCell(offset*with+3);

        outputPerMin = rows[3].createCell(offset*with+1);
        outputPerCraft = rows[3].createCell(offset*with+2);
        outputTotal = rows[3].createCell(offset*with+3);

        outputPerMin.setCellValue(recipe.getPrimaryOutputPerMin());
        outputPerCraft.setCellValue(recipe.getPrimaryOutputPerCraft());
        outputTotal.setCellFormula(outputPerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());
        Util.setBorders(outputPerMin,false,true,false,false);
        Util.setBorders(outputPerCraft,false,true,false,false);
        Util.setBorders(outputTotal,false,true,false,false);

        String craftingRatioFormula = outputPerMin.getAddress().formatAsString() + "/" + outputPerCraft.getAddress().formatAsString();

        if(recipe.isHasSecondOutput()){
            output2PerMin.setCellFormula(craftingRatioFormula +"*"+output2PerCraft.getAddress().formatAsString());
            output2PerCraft.setCellValue(recipe.getSecondOutputPerCraft());
            output2Total.setCellFormula(output2PerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());
        }

        input0PerMin = rows[4].createCell(offset*with+1);
        input0PerCraft = rows[4].createCell(offset*with+2);
        input0Total = rows[4].createCell(offset*with+3);

        input1PerMin = rows[5].createCell(offset*with+1);
        input1PerCraft = rows[5].createCell(offset*with+2);
        input1Total = rows[5].createCell(offset*with+3);

        input2PerMin = rows[6].createCell(offset*with+1);
        input2PerCraft = rows[6].createCell(offset*with+2);
        input2Total = rows[6].createCell(offset*with+3);

        input3PerMin = rows[7].createCell(offset*with+1);
        input3PerCraft = rows[7].createCell(offset*with+2);
        input3Total = rows[7].createCell(offset*with+3);

        Util.setBorders(input3PerMin,false,true,false,false);
        Util.setBorders(input3PerCraft,false,true,false,false);
        Util.setBorders(input3Total,false,true,false,false);

        input0PerMin.setCellFormula(craftingRatioFormula +"*"+input0PerCraft.getAddress().formatAsString());
        input0PerCraft.setCellValue(recipe.getInput0PerCraft());
        input0Total.setCellFormula(input0PerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());

        if(recipe.getNumInputs()>=2) {
            input1PerMin.setCellFormula(craftingRatioFormula +"*"+input1PerCraft.getAddress().formatAsString());
            input1PerCraft.setCellValue(recipe.getInput1PerCraft());
            input1Total.setCellFormula(input1PerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());
        }

        if(recipe.getNumInputs()>=3) {
            input2PerMin.setCellFormula(craftingRatioFormula +"*"+input2PerCraft.getAddress().formatAsString());
            input2PerCraft.setCellValue(recipe.getInput2PerCraft());
            input2Total.setCellFormula(input2PerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());
        }

        if(recipe.getNumInputs()>=4) {
            input3PerMin.setCellFormula(craftingRatioFormula +"*"+input3PerCraft.getAddress().formatAsString());
            input3PerCraft.setCellValue(recipe.getInput3PerCraft());
            input3Total.setCellFormula(input3PerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());
        }




    }

    static final int with = 4;

    private Cell numberMachines,output2PerMin,output2PerCraft,output2Total,outputPerMin,outputPerCraft,outputTotal,input0PerMin,input0PerCraft,input0Total,input1PerMin,input1PerCraft,input1Total,input2PerMin,input2PerCraft,input2Total,input3PerMin,input3PerCraft,input3Total;

    private final Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }


}
