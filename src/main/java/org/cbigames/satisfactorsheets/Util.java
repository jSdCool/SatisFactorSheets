package org.cbigames.satisfactorsheets;

import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.json.JSONArray;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Util {
    /**saves a JSONArray to a file
     * @param arr the json array to save
     * @param file the name of the file to save to
     */
    public static void saveJSONArray(JSONArray arr, String file){
        //save the file
        try {
            OutputStream out;
            //automatically apply gzip compression if specified at the end of the file path
            if(file.endsWith(".gz")){
                out = new GZIPOutputStream(new FileOutputStream(file));
            }else{
                out = new FileOutputStream(file);
            }
            saveJSONArray(arr,out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**saves a JSONArray to a file
     * @param arr the json array to save
     * @param out the output stream to write the file to. NOTE: the stream will NOT be closed
     */
    public static void saveJSONArray(JSONArray arr, OutputStream out){
        //save the file
        try {
            Writer wr = new PrintWriter(out);
            arr.write(wr,2,1);
            wr.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**since there is not buit in method to load a json array from a file and the process requires some effort, this method creates a json array from  json file
     * @param filePath the path to the json file
     * @return json array created from the file
     */
    public static JSONArray loadJSONArray(String filePath) throws IOException{
        InputStream in;
        //if the json file is compressed as gzip then automatically decompress it
        if(filePath.endsWith(".gz")){
            in = new GZIPInputStream(new FileInputStream(filePath));
        }else{
            in = new FileInputStream(filePath);
        }

        return loadJSONArray(in);

    }

    public static JSONArray loadJSONArray(InputStream in) throws IOException{
        StringBuilder rawContent = new StringBuilder();
        int bytesRead;
        byte[] buffer = new byte[1024];
        while((bytesRead = in.read(buffer)) != -1) {
            rawContent.append(new String(buffer, 0, bytesRead));
        }

        return new JSONArray(rawContent.toString());

    }

    static public void setBorders(Cell cell, boolean top, boolean bottom, boolean left, boolean right){
        PropertyTemplate pt = new PropertyTemplate();
        CellRangeAddress cra = new CellRangeAddress(cell.getRowIndex(),cell.getRowIndex(),cell.getColumnIndex(),cell.getColumnIndex());
        if(top){
            pt.drawBorders(cra, BorderStyle.MEDIUM, BorderExtent.TOP);
        }
        if(bottom){
            pt.drawBorders(cra, BorderStyle.MEDIUM, BorderExtent.BOTTOM);
        }
        if(left){
            pt.drawBorders(cra, BorderStyle.MEDIUM, BorderExtent.LEFT);
        }
        if(right){
            pt.drawBorders(cra, BorderStyle.MEDIUM, BorderExtent.RIGHT);
        }
        pt.applyBorders(cell.getSheet());
    }
}
