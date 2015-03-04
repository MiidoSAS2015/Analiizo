package com.miido.miido.mcompose;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alvaro on 18/02/2015.
 */
public class interfaceBuilder {

    private objectCreator ocClass;
    private Context context;
    private Object [][] objects;

    private Boolean buildResults;

    private String [][] components;

    private String[][] optionsList;

    private int height;
    private int width;
    private int counter;

    public interfaceBuilder(Context context){
        this.context = context;
        this.buildResults = false;
        ocClass = new objectCreator(this.context);
    }

    public void starInterfaceBuilder(){
        this.counter = 0;
        objects = new Object[components.length][4];
        if(this.components.length > 0){
            if(this.components[0].length > 0){
                if(buildInterface()){
                    this.buildResults = true;
                }
            }
        } else{
            this.buildResults = false;
        }
    }

    private Boolean buildInterface(){
        try {
            for (String[] component : this.components) {
                ocClass.setName(component[1]);
                ocClass.setLabel(component[2]);
                ocClass.setType(decodeObjectType(component[3]));
                ocClass.setRequired(Boolean.parseBoolean(component[4]));
                ocClass.setHint(component[5]);//2
                ocClass.setInputRules(decodeObjectRules(component[6]));//2
                ocClass.setMaxLength(Integer.parseInt(component[7]));//2
                ocClass.setOptionsList(findOptionsAvailable(Integer.parseInt(component[0])));
                this.objects[this.counter][0] = ocClass.buildObject();
                this.objects[this.counter][1] = component[2];
                this.objects[this.counter][2] = component[9];//2
                this.objects[this.counter][3] = component[0];
                this.counter++;
            }
            return true;
        } catch (Exception ex){
            Log.e("buildInterfaceExc::", "bie_"+ex.toString());
            return false;
        }
    }

    public int decodeObjectType(String type){
        switch (type){
            case "tf":
                return 1;
            case "dp":
                return 2;
            case "cb":
                return 3;
            case "sp":
                return 8;
            default:
                return 0;
        }
    }

    public int decodeObjectRules(String rules){

        switch (rules){
            case "vch":
                return 0;
            case "int":
                return 1;
            case "eml":
                return 2;
            case "dec":
                return 3;
            default:
                return 0;
        }
    }

    private List<String> findOptionsAvailable(int id){
        List<String> optionsFound = new ArrayList<String>();
        for (String[] option: this.optionsList){
            String[] relId = option[1].split(",");
            for(String id_tmp : relId) {
                if (Integer.parseInt(id_tmp) == id) {
                    for (int c = 2; c < option.length; c++) {
                        optionsFound.add(c - 2, option[c]);
                    }
                }
            }
        }
        return optionsFound;
    }

    //SETTERS
    public boolean setArrayValue(String[][] components){
        try {
            if (this.height == components.length) {
                if (this.width == components[0].length) {
                    this.components = new String[this.height][this.width];
                    this.components = components;
                    return true;
                }
            }
        }catch (Exception e){
            Log.e("ib.sav", e.getMessage());
        }
        return false;
    }
    public void setArrayHeight(int height){
        this.height = height;
    }
    public void setArrayWidht(int width){
        this.width = width;
    }
    public void setOptionsList(String[][] optionsList){
        this.optionsList = optionsList;
    }

    //GETTERS
    public String[][] getArrayValue(){
        return this.components;
    }
    public int getArrayHeight(){
        return this.height;
    }
    public int getArrayWidth(){
        return this.width;
    }
    public Boolean getBuildResults(){
        return this.buildResults;
    }
    public Object[][] getObjects(){
        return this.objects;
    }
}