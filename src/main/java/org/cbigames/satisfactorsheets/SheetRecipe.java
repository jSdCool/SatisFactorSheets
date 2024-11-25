package org.cbigames.satisfactorsheets;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;

public class SheetRecipe {
    public SheetRecipe(Recipe recipe, Row[] rows, int offset,int overrideMachineNumber){
        this.recipe = recipe;
        this.overrideMachineNumber = overrideMachineNumber;

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

        Cell output2PerMin = rows[2].createCell(offset * with + 1);
        Cell output2PerCraft = rows[2].createCell(offset * with + 2);
        output2Total = rows[2].createCell(offset*with+3);

        outputPerMin = rows[3].createCell(offset*with+1);
        Cell outputPerCraft = rows[3].createCell(offset * with + 2);
        Cell outputTotal = rows[3].createCell(offset * with + 3);

        outputPerMin.setCellValue(recipe.getPrimaryOutputPerMin());
        outputPerCraft.setCellValue(recipe.getPrimaryOutputPerCraft());
        outputTotal.setCellFormula(outputPerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());
        Util.setBorders(outputPerMin,false,true,false,false);
        Util.setBorders(outputPerCraft,false,true,false,false);
        Util.setBorders(outputTotal,false,true,false,false);

        String craftingRatioFormula = outputPerMin.getAddress().formatAsString() + "/" + outputPerCraft.getAddress().formatAsString();

        if(recipe.isHasSecondOutput()){
            output2PerMin.setCellFormula(craftingRatioFormula +"*"+ output2PerCraft.getAddress().formatAsString());
            output2PerCraft.setCellValue(recipe.getSecondOutputPerCraft());
            output2Total.setCellFormula(output2PerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());
        }

        Cell input0PerMin = rows[4].createCell(offset * with + 1);
        Cell input0PerCraft = rows[4].createCell(offset * with + 2);
        input0Total = rows[4].createCell(offset*with+3);

        Cell input1PerMin = rows[5].createCell(offset * with + 1);
        Cell input1PerCraft = rows[5].createCell(offset * with + 2);
        input1Total = rows[5].createCell(offset*with+3);

        Cell input2PerMin = rows[6].createCell(offset * with + 1);
        Cell input2PerCraft = rows[6].createCell(offset * with + 2);
        input2Total = rows[6].createCell(offset*with+3);

        Cell input3PerMin = rows[7].createCell(offset * with + 1);
        Cell input3PerCraft = rows[7].createCell(offset * with + 2);
        input3Total = rows[7].createCell(offset*with+3);

        Util.setBorders(input3PerMin,false,true,false,false);
        Util.setBorders(input3PerCraft,false,true,false,false);
        Util.setBorders(input3Total,false,true,false,false);

        input0PerMin.setCellFormula(craftingRatioFormula +"*"+ input0PerCraft.getAddress().formatAsString());
        input0PerCraft.setCellValue(recipe.getInput0PerCraft());
        input0Total.setCellFormula(input0PerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());

        if(recipe.getNumInputs()>=2) {
            input1PerMin.setCellFormula(craftingRatioFormula +"*"+ input1PerCraft.getAddress().formatAsString());
            input1PerCraft.setCellValue(recipe.getInput1PerCraft());
            input1Total.setCellFormula(input1PerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());
        }

        if(recipe.getNumInputs()>=3) {
            input2PerMin.setCellFormula(craftingRatioFormula +"*"+ input2PerCraft.getAddress().formatAsString());
            input2PerCraft.setCellValue(recipe.getInput2PerCraft());
            input2Total.setCellFormula(input2PerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());
        }

        if(recipe.getNumInputs()>=4) {
            input3PerMin.setCellFormula(craftingRatioFormula +"*"+ input3PerCraft.getAddress().formatAsString());
            input3PerCraft.setCellValue(recipe.getInput3PerCraft());
            input3Total.setCellFormula(input3PerMin.getAddress().formatAsString()+"*"+numberMachines.getAddress().formatAsString());
        }

    }

    static final int with = 4;

    private final Cell numberMachines;
    private final Cell output2Total;
    private final Cell outputPerMin;
    private final Cell input0Total;
    private final Cell input1Total;
    private final Cell input2Total;
    private final Cell input3Total;

    private final Recipe recipe;

    private final ArrayList<SheetRecipe> requireRecipes = new ArrayList<>();
    private final ArrayList<SheetRecipe> provideReductions = new ArrayList<>();
    private final ArrayList<Integer> requireIndex = new ArrayList<>();

    private final int overrideMachineNumber;


    public Recipe getRecipe() {
        return recipe;
    }

    public void addRequires(SheetRecipe recipe, int input){
        requireRecipes.add(recipe);
        requireIndex.add(input);
    }

    public void addReduction(SheetRecipe recipe){
        provideReductions.add(recipe);
    }

    @SuppressWarnings("all")
    public Cell getInputTotal(int index){
        switch(index){
            case 0:
                return input0Total;
            case 1:
                return input1Total;
            case 2:
                return input2Total;
            case 3:
                return input3Total;
            default:
                return null;
        }
    }

    public Cell getSecondaryOutputTotal() {
        return output2Total;
    }

    public void writeMachineCell(){
        if(overrideMachineNumber!=0){
            numberMachines.setCellValue(overrideMachineNumber);
        }else {
            String[] requiresCells = new String[requireRecipes.size()],reductionCells = new String[provideReductions.size()];
            for(int i=0;i<requireRecipes.size();i++){
                requiresCells[i] = requireRecipes.get(i).getInputTotal(requireIndex.get(i)).getAddress().formatAsString();
            }
            for(int i=0;i<provideReductions.size();i++){
                reductionCells[i]=provideReductions.get(i).getSecondaryOutputTotal().getAddress().formatAsString();
            }
            String requiredAddition = String.join("+",requiresCells);
            String reductionsSubtract = String.join("-",reductionCells);
            if(!provideReductions.isEmpty()) {
                numberMachines.setCellFormula("(" + requiredAddition + "-" + reductionsSubtract + ")/" + outputPerMin.getAddress().formatAsString());
            }else{
                numberMachines.setCellFormula("(" + requiredAddition + ")/" + outputPerMin.getAddress().formatAsString());
            }
        }
    }
}
