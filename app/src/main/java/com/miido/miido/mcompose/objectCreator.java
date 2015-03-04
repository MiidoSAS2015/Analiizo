package com.miido.miido.mcompose;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alvaro on 18/02/2015.
 */
public class objectCreator{

    private Object object;
    private Context context;

    private String name;
    private String label;
    private String hint;

    private List<String> options;

    private int type;
    private int inputRules;
    private int maxLength;

    private Boolean required;

    public objectCreator(Context context){
        this.context = context;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public void setType(int type){
        this.type = type;
    }

    public void setHint(String hint){
        this.hint = hint;
    }

    public void setRequired(Boolean required){
        this.required = required;
    }

    public void setInputRules(int rules){
        this.inputRules = rules;
    }

    public void setMaxLength(int maxLength){
        this.maxLength = maxLength;
    }

    public void setOptionsList(List<String> options){
        this.options = options;
    }

    public Object buildObject(){
        createObject();
        setObjectProperties();
        return this.object;
    }

    public void createObject(){
        switch(this.type){
            case 0:
                this.object = new ScrollView(this.context);
                break;
            case 1:
                this.object = new EditText(this.context);
                break;
            case 2:
                this.object = new DatePicker(this.context);
                break;
            case 3:
                this.object = new CheckBox(this.context);
                break;
            case 4:
                this.object = new RadioGroup(this.context);
                break;
            case 5:
                this.object = new RadioButton(this.context);
                break;
            case 6:
                this.object = new TextView(this.context);
                break;
            case 7:
                this.object = new ListPopupWindow(this.context);
                break;
            case 8:
                this.object = new Spinner(this.context);
                break;/*
            case 2:
                this.object = new android.widget
                break;
            /*case 2:
                this.object = (new DatePicker(context));
                break;
            */default:
                break;
        }
    }

    private void setObjectProperties(){
        /*try {
            Class<?> theClass = Class.forName(this.object.getClass().toString());
            //theClass.cast(this.object).setInputType(InputType.TYPE_CLASS_TEXT);
        }catch (ClassNotFoundException cnfe){
        }*/
        switch (this.type){
            case 1:
                ((EditText) this.object).setHint(this.hint);
                ((EditText) this.object).setHintTextColor(Color.DKGRAY);
                ((EditText) this.object).setTextColor(Color.BLACK);
                ((EditText) this.object).setFilters(new InputFilter[] {new InputFilter.LengthFilter(this.maxLength)});

                ((EditText) this.object).setPadding(
                        15, ((EditText) this.object).getPaddingTop(),
                        ((EditText) this.object).getPaddingRight(), ((EditText) this.object).getPaddingBottom() - 3);
                switch(this.inputRules){
                    case 0:
                        ((EditText) this.object).setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case 1:
                        ((EditText) this.object).setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case 2:
                        ((EditText) this.object).setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        break;
                    case 3:
                        ((EditText) this.object).setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 8:
                ArrayAdapter<String> aa = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, this.options);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ((Spinner) this.object).setAdapter(aa);
                break;
        }
    }
}