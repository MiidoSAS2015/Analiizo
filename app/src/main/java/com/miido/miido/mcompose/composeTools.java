package com.miido.miido.mcompose;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import com.miido.miido.mcompose.constants;

import org.json.JSONObject;

/**
 * Created by Alvaro on 25/02/2015.
 */
public class composeTools {

    private constants constants;
    private Context context;
    private structure structure;
    private Boolean[][] mJoinHandler;
    private int sFilePrefix;
    private int cFilePrefix;
    private SharedPreferences sp;
    private SharedPreferences.Editor spe;
    private OutputStreamWriter osw;
    private BufferedReader br;
    private FileOutputStream fos;
    private InputStreamReader isr;

    public composeTools(Context context){
        this.constants = new constants();
        this.context = context;
        this.structure = new structure(context);
        this.structure.setStructure();
    }

    public void startJoinHandler(){
        for(int c = 0; c < this.structure.fieldHandlerJoin.length; c++){
            this.mJoinHandler[c] = new Boolean[this.structure.fieldHandlerJoin[c].length];
        }
    }
    //Order a Bi-dimensional String Structure if ID are in [0]
    public String[][] orderStructure(String[][] structure1) throws Exception{
        if (structure1.length > 0){
            if (structure1[0].length < 1){
                return null;
            }
        } else {return null;}
        String[][] structure2 = new String[structure1.length][structure1[0].length];
        int[] assigned = new int[structure1.length];
        for (int y = 0; y < structure1.length; y++){
            int x_cur = 0;
            int x_old = 0;
            String x_old_val = "";
            if (structure2[y][0] == null) {
                structure2[y][0] = ("" + (structure2.length * 10000));
            }
            for (String[] structure1_tmp : structure1){
                Boolean found = false;
                for(int value : assigned){
                    if(value == Integer.parseInt(structure1_tmp[0])){
                        found = true;
                        break;
                    }
                }
                try {
                    if (!found) {
                        if (Integer.parseInt(structure2[y][0]) > Integer.parseInt(structure1_tmp[0])) {
                            structure2[y] = structure1_tmp;
                            if (Integer.parseInt(structure1[x_old][0]) == -1) {
                                structure1[x_old][0] = x_old_val;
                            }
                            x_old_val = structure1[x_cur][0];
                            //Toast.makeText(this.context, structure1[x_cur][0], Toast.LENGTH_SHORT).show();
                            x_old = x_cur;
                        }
                    }
                    x_cur++;
                } catch (Exception e){
                }
            }
            try {
                assigned[y] = Integer.parseInt(structure2[y][0]);
            } catch (Exception e){
            }
        }
        return structure2;
    }

    //Validate on Field Event Handler Meets All Conditions
    public Boolean validateEventConditions(String hType, String value){
        try{
            value = ""+Integer.parseInt(value);
        } catch (Exception e){
            //return false;
        }
        int maxFulfil = 0;
        int fulfiled = 0;
        int eventRownd = 0;
        String[][] hEvents = this.structure.handlerEvent;
        for(String[] structure_tmp : hEvents){
            if(structure_tmp[0].equals(hType)){
                int c = 1;
                while(true){
                    try{
                        switch (structure_tmp[c]){
                            case "=":
                                if(value.equals(structure_tmp[c+1])){
                                    fulfiled++;
                                }
                                break;
                            case ">":
                                if(Integer.parseInt(value) > Integer.parseInt(structure_tmp[c+1])){
                                    fulfiled++;
                                }
                                break;
                            case "<":
                                if(Integer.parseInt(value) < Integer.parseInt(structure_tmp[c+1])){
                                    fulfiled++;
                                }
                                break;
                            case "!=":
                                if(!(value.equals(structure_tmp[c+1]))){
                                    fulfiled++;
                                }
                                break;
                        }
                        c+=2;
                        maxFulfil = (c/2);
                    } catch (Exception rNe){
                        break;
                    }
                }
            }
            eventRownd++;
        }
        try {
            if (maxFulfil == fulfiled) {
                return true;
            }
        } catch (Exception e){
        }
        return false;
    }

    public Boolean changeEventJoinConditions(int id, Boolean meet){
        try {
            for (int c = 0; c < mJoinHandler.length; c++){
                for(int i = 0; i < this.mJoinHandler[c].length; i++){
                    if(Integer.parseInt(this.structure.fieldHandlerJoin[c][i]) == id){
                        mJoinHandler[c][i] = meet;
                        return true;
                    }
                }
            }
            return true;
        } catch (Exception e){
            return false;

        }
    }

    //Split Event
    private String[] sEvent(String handler){
        String[] h = handler.split(".");
        switch (h[0]){
            case "YY":
                return h;
            case "MM":
                return h;
            case "DD":
                return h;
            default:
                return (null);
        }
    }

    //Seek if a field have a subForm
    public int findSubFormIndex(int id){
        int c = 0;
        for (String[] structure_temp : this.structure.forms){
            if(Integer.parseInt(structure_temp[2]) == id ){
                return c;
            }
            c++;
        }
        return -1;
    }

    //Create or Read Local Config
    public Boolean corLocalConfig(){
        try {
            sp = this.context.getSharedPreferences(this.constants.localSharedPreferences_name, Context.MODE_PRIVATE);
            spe = sp.edit();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //Find or Create Prefixes
    public Boolean focPrefixes(){
        try {
            sFilePrefix = sp.getInt("sFilePrefix", 0);
            cFilePrefix = sp.getInt("cFilePrefix", 0);
            if(this.sPrefixes()){
                return true;
            } else {
                return true;
            }
        } catch (Exception e){
            return false;
        }
    }

    //Save Prefixes
    private Boolean sPrefixes(){
        try{
            spe.putInt("sFilePrefix", sFilePrefix);
            spe.putInt("cFilePrefix", cFilePrefix);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //Find or Create Local File
    public Boolean focLocalFile(){
        try {
            this.fos = (this.context.openFileOutput(this.cFilePrefix+"."+this.constants.localFile_name, Context.MODE_PRIVATE));
            this.osw = new OutputStreamWriter(fos);
            //this.osw.close();
            return true;
        } catch (Exception e) {
            toast("no escribio archivo", 0);
            return false;
        }
    }

    //Write on Local File
    public Boolean wLocalFile(String Field, String Value){
        try {
            osw.write("{"+Field+":"+Value+"}");
            osw.close();
            return true;
        } catch (Exception e){
            toast(e.getMessage(), 1);
            return false;
        }
    }

    //Read the Local File
    public Boolean readLocalFile(){
        try {
            this.isr = new InputStreamReader(this.context.openFileInput(this.cFilePrefix+"."+this.constants.localFile_name));
            this.br = new BufferedReader(this.isr);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //Find if is Open the Local File
    public Boolean fioLocalFile(){
        try{
            if(this.readLocalFile()) {
                //toast(cFilePrefix+"--"+this.br.readLine(), 1);
                return true;
            } return false;
        } catch (Exception e){
            return false;
        }
    }

    //Close Local File
    public Boolean cLocalFile(){
        try {
            osw.write("};");
            osw.close();
            cFilePrefix++;
            if(sPrefixes()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }

    public void animateToolBar(){

    }



    public void toast(String msj, int tCase){
        if(tCase == 1)
            Toast.makeText(this.context, msj, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this.context, msj, Toast.LENGTH_LONG).show();
    }
}