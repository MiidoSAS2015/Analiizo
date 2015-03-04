package com.miido.miido;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
//Miido Classes
import com.miido.miido.mcompose.interfaceBuilder;
import com.miido.miido.mcompose.structure;
import com.miido.miido.mcompose.constants;
import com.miido.miido.mcompose.composeTools;

public class main extends Activity{

    private interfaceBuilder ibClass;
    private structure structureObject;
    private constants constants;
    private composeTools tools;

    private FrameLayout rl_o;
    private TableLayout tLayout;
    private TableLayout[] tLayoutForms;
    private LinearLayout rlNBButton;
    private RelativeLayout[] rl_s;
    private Button oButton;
    private Button nButton;
    private Button bButton;
    private Object[] fl;
    private Object dateObject;

    private String[][] structure;

    private int displayWidth;
    private int displayHeight;
    private int y,m,d;
    private int currentForm;
    private int rows;
    private int[] maxScroll;
    private int bof;
    private int maxDisplaySize;

    private Boolean oStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);
        //Starting environment
        //setOrientation
        //setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        maxDisplaySize = 20600;
        //Creating objects instances
        prepareObjects();
        //Prepare AND/OR create storage environment

        if(prepareStorage()) {
            //Obtaining Display Metrics
            getDisplaySize();
            //Obtaining parent object to apply new interface
            tLayout = getInterfaceComponents();
            //Create Options menu
            createOptionsInterface();
            //initialize layouts
            initializeFLayouts();
            //Gathering form structure
            setStructureData();
            //Starting objects generator
            ibClass.starInterfaceBuilder();
            //pasting objects to scrollView container
            if (ibClass.getBuildResults()) {
                createInterface();
                currentForm = 0;
                //showForm();
            } else {
                Toast.makeText(this, "No interface found", Toast.LENGTH_LONG).show();
                Log.i("MainException::", "Couldn't generate interface objects");
            }
            //tLayout.setVerticalScrollBarEnabled(true);
        }

    }

    private void setScreenOrientation(int i){
        setRequestedOrientation(i);
    }
    private void prepareObjects(){
        ibClass = new interfaceBuilder(getApplicationContext());
        tools = new composeTools(getApplicationContext());
        rl_o = ((FrameLayout) findViewById(R.id.superF));
        oButton = (Button) findViewById(R.id.sOptions);
        fl = new Object[1000];
        structureObject = new structure(getApplicationContext());
        structureObject.setStructure();
        constants = new constants();
        tLayoutForms = new TableLayout[(this.structureObject.forms.length)];
        rl_s = new RelativeLayout[(this.structureObject.forms.length)];
        oStatus = false;
    }
    @SuppressWarnings("Unused")
    private Boolean prepareStorage(){
        int caseError = 0;
        //Create or read local application config
        if(tools.corLocalConfig()) {
            //Create or read file prefixes
            if(tools.focPrefixes()) {
                //Create or read local file
                if (tools.focLocalFile()) {
                    //if file isn't closed, resume it
                    if (tools.fioLocalFile()) {
                       return true;
                    } else caseError = 4;
                } else caseError = 3;
            } else caseError = 2;
        } else caseError = 1;
        return false;
        //AlertDialog("Error case ", caseError+"");
    }
    private TableLayout getInterfaceComponents(){
        this.displayHeight = getResources().getDisplayMetrics().heightPixels;
        this.displayWidth  = getResources().getDisplayMetrics().widthPixels;
        return ((TableLayout) findViewById(R.id.parentFLayout));
    }
    private void setStructureData(){
        try {
            structure = tools.orderStructure(structureObject.structure);
        } catch (Exception e){
            AlertDialog("Error", e.getMessage());
        }
        ibClass.setArrayHeight(structure.length);
        ibClass.setArrayWidht(structure[0].length);
        ibClass.setArrayValue(structure);
        ibClass.setOptionsList(structureObject.options);
    }
    private void getDisplaySize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.displayHeight = dm.heightPixels;
        this.displayWidth = dm.widthPixels;
    }
    private void createInterface(){
        Object[][] object;
        object = ibClass.getObjects();
        object = orderForms(object);
        int lY[] = new int[this.structureObject.formsOrder.length];
        maxScroll = new int[this.structureObject.formsOrder.length];
        try{
            //adding objects to main
            if(object.length>0) {
                int counter = 0;
                int[] f;
                f = new int[this.structureObject.forms.length];
                for (Object[] object_tmp : object) {
                    RelativeLayout flt = new RelativeLayout(this);
                    rows = 1;
                    if(this.displayWidth > maxDisplaySize) { rows = 2; }
                    LinearLayout.LayoutParams lpf;
                    if(Integer.parseInt(structureObject.forms[currentForm][2]) == 0)
                        if(rows == 1){
                            lpf = new LinearLayout.LayoutParams((this.displayWidth/rows), 85);
                        } else {
                            lpf = new LinearLayout.LayoutParams((this.displayWidth / rows) - 100, 85);
                        }
                    else
                        lpf = new LinearLayout.LayoutParams((this.displayWidth/rows)-100, 0);
                    TextView tv = new TextView(getApplicationContext());
                    //tv.setTextColor(Color.parseColor("#00cc33"));
                    tv.setTextColor(Color.BLACK);
                    tv.setText((String)object_tmp[1]);
                    flt.setLayoutParams(lpf);

                    if(rows == 2){
                        flt.setX(((f[Integer.parseInt((String)object_tmp[2])]) * (this.displayWidth / rows)) + 10);
                        flt.setY(flt.getY()-lY[Integer.parseInt((String)object_tmp[2])]);
                        maxScroll[Integer.parseInt((String)object_tmp[2])] = (Math.round(flt.getY()));
                        if(f[Integer.parseInt((String)object_tmp[2])]==0){
                            lY[Integer.parseInt((String)object_tmp[2])] += 85;
                            f[Integer.parseInt((String)object_tmp[2])]++;
                        } else{
                            f[Integer.parseInt((String)object_tmp[2])]=0;
                            lY[Integer.parseInt((String)object_tmp[2])] -= 20;
                        }
                    } else{
                        flt.setX(10);
                        flt.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    tv.setTextSize(16);
                    fl[counter] = flt;
                    tLayoutForms[Integer.parseInt((String) object_tmp[2])-1].addView((RelativeLayout) fl[counter]);
                    flt.addView(tv);
                    int type = 0;
                    int sf = tools.findSubFormIndex(Integer.parseInt(this.structure[counter][0]));

                    if(object_tmp[0].getClass().equals(EditText.class)) {
                        ((EditText) object_tmp[0]).setBackgroundResource(R.drawable.focus_border_style);
                        ((EditText) object_tmp[0]).setY(27);
                        ((EditText) object_tmp[0]).setWidth((this.displayWidth / 2));
                        type = 2;
                        flt.addView((EditText) object_tmp[0]);
                    } else
                    if(object_tmp[0].getClass().equals(DatePicker.class)) {
                        EditText et = new EditText(this);
                        et.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                        et.setBackgroundResource(R.drawable.focus_border_style);
                        et.setY(27);
                        et.setHint("DD/MM/AAAA");
                        et.setHintTextColor(Color.DKGRAY);
                        et.setWidth((this.displayWidth / 2));
                        addListenerOnObject(et, 2, 0, 0, ((Integer) object_tmp[3]));
                        flt.addView(et);
                        //type = ?;
                    } else
                    if(object_tmp[0].getClass().equals(Spinner.class)) {
                        ((Spinner) object_tmp[0]).setBackgroundResource(R.drawable.spinner);
                        ((Spinner) object_tmp[0]).setY(27);
                        ((Spinner) object_tmp[0]).setLayoutParams(new ActionBar.LayoutParams(this.displayWidth / 2, 50));
                        flt.addView((Spinner) object_tmp[0]);
                        type = 8;
                    } else
                    if(object_tmp[0].getClass().equals(CheckBox.class)){
                        RelativeLayout rl_tmp = new RelativeLayout(this);

                        flt.removeView(tv);
                        rl_tmp.addView(((CheckBox) object_tmp[0]));
                        rl_tmp.addView(tv);
                        flt.addView(rl_tmp);

                        ((CheckBox) object_tmp[0]).setBackgroundResource(R.drawable.checkbox);
                        ((CheckBox) object_tmp[0]).setX(20);

                        tv.setX(50);
                        tv.setY(tv.getY() + 5);
                        flt.getLayoutParams().height = 27;
                        flt.setGravity(Gravity.CENTER_HORIZONTAL);
                        rl_tmp.setGravity(Gravity.LEFT);
                        rl_tmp.getLayoutParams().width = ((displayWidth/2)+50);

                        type = 3;
                    }
                    //AlertDialog("Ad", this.structure[counter].length+"");

                    if(Integer.parseInt(this.structure[counter][12]) != 0){
                       // addListenerOnObject(object_tmp[0], type, 1,//Integer.parseInt(this.structure[counter][13])
                       //         sf, Integer.parseInt((String) object_tmp[3]));
                    }
                    /*
                    try {
                        if (sf > -1 ) {
                            rl_s[sf] = new RelativeLayout(this);
                            if (Integer.parseInt(structureObject.forms[sf][3]) != 0) {
                                tLayoutForms[Integer.parseInt((String) object_tmp[2])].addView(rl_s[sf]);
                                rl_s[sf].addView(tLayoutForms[sf]);
                                rl_s[sf].setContentDescription(rl_s[sf].getLayoutParams().height+"");
                            }
                            rl_s[sf].setVisibility(View.INVISIBLE);
                            rl_s[sf].setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                        }
                    } catch (Exception e){
                        AlertDialog("Error", "SF_rlCreator::"+e.getMessage());
                    }*/
                    counter++;
                }
                createNextBackButton();
            }
        }catch(Exception ex){
            AlertDialog("mainException", ex.getMessage().toString());
            //Log.e("MainException::", ex.getMessage());
        }
    }
    private void createNextBackButton(){
        this.rlNBButton = new LinearLayout(this);
        this.rlNBButton.setGravity(Gravity.CENTER_HORIZONTAL);

        nButton = new Button(getApplicationContext());
        bButton = new Button(getApplicationContext());

        nButton.setText(constants.nButton);
        bButton.setText(constants.bButton);
        nButton.setTextColor(Color.WHITE);
        bButton.setTextColor(Color.WHITE);
        nButton.setBackgroundResource(R.drawable.button);
        bButton.setBackgroundResource(R.drawable.button);
        bButton.setX(-5);
        nButton.setX(5);

        this.rlNBButton.addView(bButton);
        this.rlNBButton.addView(nButton);
    }
    private Object[][] orderForms(Object[][] object){
        try {
            int i = 0;
            for (Object[] object_tmp : object) {
                for (int c = 0; c < this.structureObject.formsOrder.length; c++) {
                    try {
                        if (object_tmp[2] == this.structureObject.formsOrder[c]) {
                            object[i][2] = (c + "");
                        }
                    } catch (Exception e){
                    }
                }
                i++;
            }
        }catch (Exception e){}
        return object;
    }
    private void initializeFLayouts(){
        try {
            for (int c = 0; c < tLayoutForms.length; c++) {
                tLayoutForms[c] = new TableLayout(getApplicationContext());
                if (!(this.structureObject.forms[c][1].equals("")))
                    tLayoutForms[c].addView(this.createHeader(this.structureObject.forms[c][1]));
            }
        } catch (Exception e){
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    private void showForm(){
        for(TableLayout tl_temp : tLayoutForms){
            try{
                tl_temp.removeView(rlNBButton);
            } catch (Exception e){}
        }
        try {
            if(Integer.parseInt(structureObject.forms[currentForm][2]) == 0) {
                try {
                    if(currentForm < tLayoutForms.length-1) {
                        tLayoutForms[currentForm].addView(rlNBButton);
                    }
                } catch (Exception e){AlertDialog("", e.getMessage());}
                tLayout.addView(tLayoutForms[currentForm]);
                if (rows > 1) {
                    if (maxScroll[currentForm] < this.displayHeight) {
                        maxScroll[currentForm] = this.displayHeight;
                    }
                    tLayoutForms[currentForm].getLayoutParams().height = this.displayHeight - 100;
                }/* else if (maxScroll[currentForm] < this.displayHeight){
                    tLayoutForms[currentForm].getLayoutParams().height = this.displayHeight - 100;
                }*/
                addNextListener();
                addBackListener();
            } else{
                if((rl_s[currentForm].getVisibility() == View.VISIBLE) && (Integer.parseInt(structureObject.forms[currentForm][3]) == 0)){
                    try {
                        tLayoutForms[currentForm].addView(rlNBButton);
                    } catch (Exception e){AlertDialog("", e.getMessage());}
                    tLayout.addView(tLayoutForms[currentForm]);
                } else {
                    if (bof == 1) {
                        currentForm++;
                    } else if (bof == -1) {
                        currentForm--;
                    }
                    showForm();
                }
            }
        } catch (Exception e){
            tools.toast("Estas en el final de la encuesta", 1);
            bof = -1;
            currentForm--;
            showForm();
        }

    }
    private void addListenerOnObject(final Object object, final int type, final int hType, final int sf, final int id) {
        switch (type){
            case 1:
                break;
            case 2:
                if(hType == 0) {
                    this.dateObject = object;
                    ((EditText) object).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        @SuppressWarnings("ALL")
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                showDialog(1);
                            }
                        }
                    });
                    ((EditText) object).setOnClickListener(new View.OnClickListener() {

                        @Override
                        @SuppressWarnings("ALL")
                        public void onClick(View v) {
                            showDialog(1);
                        }
                    });
                    ((EditText) object).setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            return true;
                        }
                    });
                } else{
                    ((EditText) object).setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            ((EditText) object).setContentDescription(""+sf);
                            if(event.getAction() == KeyEvent.ACTION_UP) {
                                if(tools.validateEventConditions(hType+"", ((EditText) v).getText().toString())){
                                    showSubForm(Integer.parseInt(v.getContentDescription() + ""));
                                } else{
                                    hideSubForm(Integer.parseInt(v.getContentDescription()+""));
                                }
                            }
                            return false;
                        }
                    });
                }
                break;
            case 3:
                ((CheckBox) object).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        ((CheckBox) object).setContentDescription(sf+"");
                        if(tools.validateEventConditions(hType+"", isChecked+"")){
                            showSubForm(Integer.parseInt(buttonView.getContentDescription().toString()));
                        } else{
                            hideSubForm(Integer.parseInt(buttonView.getContentDescription().toString()));
                        }
                    }
                });
                break;
            case 8:
                ((Spinner) object).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (parent.getItemAtPosition(position).toString()){
                            case "-":
                                break;
                            default:
                                ((Spinner) object).setContentDescription(""+sf);
                                if(tools.validateEventConditions(hType+"", parent.getItemAtPosition(position).toString())) {
                                    //if(changeEventJoinConditions())
                                    showSubForm(Integer.parseInt(parent.getContentDescription().toString()));
                                } else{
                                    hideSubForm(Integer.parseInt(parent.getContentDescription().toString()));
                                }
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case 100:
                ((Button) object).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionSlideToggle();
                    }
                });
                break;
        }
    }
    private void showSubForm(int sf){
        if(displayWidth>maxDisplaySize){
            tLayoutForms[currentForm].setLayoutParams(new LinearLayout.LayoutParams(displayWidth, maxScroll[currentForm]+maxScroll[sf]));
        } if(Integer.parseInt(this.structureObject.forms[sf][3]) != 0) {
            rl_s[sf].setLayoutParams(new LinearLayout.LayoutParams(displayWidth, Integer.parseInt(rl_s[sf].getContentDescription().toString())));
        }
        rl_s[sf].setVisibility(View.VISIBLE);
    }
    private void hideSubForm(int sf){
        if(displayWidth>maxDisplaySize){
            tLayoutForms[currentForm].setLayoutParams(new LinearLayout.LayoutParams(displayWidth, maxScroll[currentForm]-maxScroll[sf]));
        }
        rl_s[sf].setLayoutParams(new LinearLayout.LayoutParams(displayWidth, 0));
        rl_s[sf].setVisibility(View.INVISIBLE);
    }
    private void addNextListener(){
        this.nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bof < tLayoutForms.length) {
                    bof = 1;
                    removeCurrentForm();
                    incrementCurrentForm();
                    showForm();
                }
            }
        });
    }
    private void addBackListener(){
        this.bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bof > 0) {
                    bof = -1;
                    removeCurrentForm();
                    decrementCurrentForm();
                    showForm();
                } else{
                    tools.toast("Estas en el inicio de la encuesta", 1);
                }
            }
        });
    }
    private void removeCurrentForm(){
        tLayout.removeView(tLayoutForms[currentForm]);
    }
    private void incrementCurrentForm(){
        this.currentForm++;
    }
    private void decrementCurrentForm(){
        this.currentForm--;
    }
    private RelativeLayout createHeader(String tittle){
        RelativeLayout rl = new RelativeLayout(getApplicationContext());
        rl.setLayoutParams(new LinearLayout.LayoutParams(this.displayWidth, 35));
        TextView tv = new TextView(getApplicationContext());
        tv.setText(tittle);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(16);
        rl.addView(tv);
        rl.setGravity(Gravity.CENTER_HORIZONTAL);
        return rl;
    }
    private void createOptionsInterface(){
        try {
            rl_o.setBackgroundColor(Color.BLACK);
            rl_o.setAlpha(0.5F);
            rl_o.setY(displayHeight-25);
            oButton.setBackgroundResource(R.drawable.options_button);
            addListenerOnObject(((Object) oButton), 100, 0, 0, 0);
        } catch (Exception e){Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();}
    }
    private void optionSlideToggle(){
        if(oStatus){
            oStatus = false;
            oButton.setText("^");
            oButton.animate().setDuration(1000).translationY(0);
            rl_o.animate().setDuration(1000).translationY(displayHeight-25);
        } else{
            oStatus = true;
            oButton.setText("v");
            rl_o.animate().setDuration(1000).translationY(0);
            oButton.animate().setDuration(1000).translationY((displayHeight-75)*-1);
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new DatePickerDialog(this, datePickerListener,
                        this.y, this.m, this.d);
        }
        return null;
    }
    private void AlertDialog(String Tittle, String Message){
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(Tittle);
        ad.setMessage(Message);
        ad.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        ad.show();
    }

    @Override
    public void onBackPressed(){
        if(bof > 0) {
            bof = -1;
            removeCurrentForm();
            decrementCurrentForm();
            showForm();
        } else{
            Toast.makeText(this, "Estas en el inicio de la encuesta", Toast.LENGTH_LONG).show();
        }
    }

    //Additional Objects
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            y = selectedYear;
            m = selectedMonth;
            d = selectedDay;
            ((EditText)dateObject).setText(new StringBuilder().append(d).append("-").append(m+1).append("-").append(y));
        }
    };
}