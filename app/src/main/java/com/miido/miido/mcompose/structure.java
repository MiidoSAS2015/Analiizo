package com.miido.miido.mcompose;

import android.content.Context;
import android.widget.Toast;

import com.miido.miido.util.ConnectorHttpJSON;
import com.miido.miido.mcompose.constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alvaro on 18/02/2015.
 */
public class structure {

    private constants constants;
    private String allStructure;

    public String[][] structure;
    public String[][] options;
    public String[][] forms;
    public String[]   formsOrder;
    public String[][] handlerEvent;
    public String[][] fieldHandlerJoin;
    JSONObject jStructure;
    private Context context;

    public structure(Context context){
        this.constants = new constants();
        this.allStructure = this.constants.fileJ;
        this.context = context;
    }

    public void JSONFile(){
        ConnectorHttpJSON connection = new ConnectorHttpJSON("");
        try {
            String something = connection.execute();
            Toast.makeText(context, something, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this.context, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void setStructure(){
        try {
            this.allStructure = this.allStructure.trim();
            jStructure = new JSONObject(this.allStructure);
            setVariables();
        } catch (Exception e){
            Toast.makeText(this.context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void setVariables() throws JSONException{
        JSONArray jArray;
        //fields_structure
        jArray = jStructure.getJSONArray("fields_structure");
        structure = new String[jArray.length()][13];
        for (int c = 0; c < jArray.length(); c++){
            Object tmp = jArray.get(c);
            tmp = ((String) tmp).replace("}", "");
            String[] sTmp = tmp.toString().split(",");
            for (int i = 0; i < sTmp.length; i++) {
                String[] s_tmp = sTmp[i].split(":");
                structure[c][i] = s_tmp[1].replace("\"", "");
            }
        }
        //options
        jArray = jStructure.getJSONArray("options");
        options = new String[jArray.length()][0];
        for (int c = 0; c < jArray.length(); c++){
            JSONObject jObj_tmp = jArray.getJSONObject(c);
            JSONArray jArray_tmp = jObj_tmp.getJSONArray("Field");
            jObj_tmp.remove("Field");
            String[] sTmp = jObj_tmp.toString().split(",");
            options[c] = new String[sTmp.length];
            for (int i = 0; i < sTmp.length; i++) {
                if(i==1){
                    String fields_tmp = jArray_tmp.toString();
                    fields_tmp = fields_tmp.replace("\"", "");
                    fields_tmp = fields_tmp.substring(1, fields_tmp.length()-1);
                    options[c][i] = fields_tmp;
                } else {
                    String[] s_tmp = sTmp[i].split(":");
                    options[c][i] = s_tmp[1].replace("\"", "");
                }
            }
        }
        //forms
        jArray = jStructure.getJSONArray("forms");
        forms = new String[jArray.length()][4];
        for (int c = 0; c < jArray.length(); c++){
            Object tmp = jArray.get(c);
            String[] sTmp = tmp.toString().split(",");
            for (int i = 0; i < sTmp.length; i++) {
                String[] s_tmp = sTmp[i].split(":");
                forms[c][i] = s_tmp[1].replace("\"", "");
            }
        }
        //forms_order
        jArray = jStructure.getJSONArray("forms_order");
        formsOrder = new String[jArray.length()];
        for (int c = 0; c < jArray.length(); c++){
            Object tmp = jArray.get(c);
            String[] sTmp = tmp.toString().split(",");
            for (int i = 0; i < sTmp.length; i++) {
                String[] s_tmp = sTmp[i].split(":");
                formsOrder[c] = s_tmp[1].replace("\"", "");
            }
        }
        //handler_event
        jArray = jStructure.getJSONArray("handler_event");
        handlerEvent = new String[jArray.length()][0];
        for (int c = 0; c < jArray.length(); c++){
            Object tmp = jArray.get(c);
            String[] sTmp = tmp.toString().split(",");
            handlerEvent[c] = new String[sTmp.length];
            for (int i = 0; i < sTmp.length; i++) {
                String[] s_tmp = sTmp[i].split(":");
                handlerEvent[c][i] = s_tmp[1].replace("\"", "");
            }
        }

    }
}
