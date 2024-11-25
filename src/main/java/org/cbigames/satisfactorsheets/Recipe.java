package org.cbigames.satisfactorsheets;

import org.json.JSONObject;

public class Recipe {
    public Recipe(JSONObject input){
        outputItem = input.getString("Primary Output Item");
        primaryOutputPerMin = input.getDouble("Primary Per Min");
        primaryOutputPerCraft = input.getDouble("Primary Per Craft");
        if(input.has("Second Output Item")){
            hasSecondOutput = true;
            secondOutputItem = input.getString("Second Output Item");
            secondOutputPerCraft = input.getDouble("Second Output Per Craft");
        }
        numInputs = input.getInt("Num Inputs");

        input0Item = input.getString("Input 0 Item");
        input0PerCraft = input.getDouble("Input 0 Per Craft");
        switch (numInputs) {

            case 4:
                input3Item = input.getString("Input 3 Item");
                input3PerCraft = input.getDouble("Input 3 Per Craft");
            case 3:
                input2Item = input.getString("Input 2 Item");
                input2PerCraft = input.getDouble("Input 2 Per Craft");
            case 2:
                input1Item = input.getString("Input 1 Item");
                input1PerCraft = input.getDouble("Input 1 Per Craft");

        }

    }

    private final String outputItem;
    private String secondOutputItem = null;
    private final String input0Item;
    private String input1Item=null;
    private String input2Item=null;
    private String input3Item=null;
    private final double primaryOutputPerMin;
    private final double primaryOutputPerCraft;
    private double secondOutputPerCraft=0;
    private final double input0PerCraft;
    private double input1PerCraft=0;
    private double input2PerCraft=0;
    private double input3PerCraft=0;
    private boolean hasSecondOutput = false;

    private final int numInputs;

    public boolean isHasSecondOutput() {
        return hasSecondOutput;
    }

    public double getInput0PerCraft() {
        return input0PerCraft;
    }

    public double getInput1PerCraft() {
        return input1PerCraft;
    }

    public double getInput2PerCraft() {
        return input2PerCraft;
    }

    public double getInput3PerCraft() {
        return input3PerCraft;
    }

    public double getPrimaryOutputPerCraft() {
        return primaryOutputPerCraft;
    }

    public double getPrimaryOutputPerMin() {
        return primaryOutputPerMin;
    }

    public double getSecondOutputPerCraft() {
        return secondOutputPerCraft;
    }

    public String getOutputItem() {
        return outputItem;
    }

    public String getSecondOutputItem() {
        return secondOutputItem;
    }

    public String getInput0Item() {
        return input0Item;
    }

    public String getInput1Item() {
        return input1Item;
    }

    public String getInput2Item() {
        return input2Item;
    }

    public String getInput3Item() {
        return input3Item;
    }

    public int getNumInputs() {
        return numInputs;
    }

    @SuppressWarnings("all")
    public String getInput(int index){
        switch (index){
            case 0:
                return input0Item;
            case 1:
                return input1Item;
            case 2:
                return input2Item;
            case 3:
                return input3Item;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return getOutputItem();
    }
}
