package com.miido.miido;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miido.miido.mcompose.constants;
import com.miido.miido.util.miido;

/**
 * Created by Alvaro on 03/03/2015.
 */
public class login extends Activity{

    private constants constants;
    private miido miido;

    private Button login;
    private EditText userT, passT;
    private String errorsF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login);
        setInterface();
        setListenners();
    }

    private void setInterface(){
        createInstances();
        this.userT.setBackgroundResource(R.drawable.focus_border_style);
        this.passT.setBackgroundResource(R.drawable.focus_border_style);
        this.login.setBackgroundResource(R.drawable.button);
        this.login.setTextColor(Color.WHITE);
    }

    private void createInstances(){
        this.constants = new constants();
        this.miido = new miido();
        this.login = ((Button) findViewById(R.id.login));
        this.userT = ((EditText) findViewById(R.id.userTxt));
        this.passT = ((EditText) findViewById(R.id.passwordTxt));
    }

    private void setListenners(){
        addEventListener(0);
    }

    private void addEventListener(int element){
        switch (element){
            case 0:
                this.login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //toast("er");
                        login();
                    }
                });
                break;
        }
    }

    private void login(){
        this.errorsF = "";
        if((this.userT.getText()+"").equals("")){
            this.errorsF = this.constants._ADV001;
        } else if ((this.passT.getText()+"").equals("")){
            this.errorsF = this.constants._ADV002;
        } else{
            this.miido.setKey(this.constants._KEY);
            try {
                //byte[] tmp = this.miido.encrypt(this.userT.getText().toString());
                if((this.userT.getText()+"").equals("Fabio")){
                    if ((this.passT.getText()+"").equals("fabio123")){
                        Intent i = new Intent(this, main.class);
                        startActivity(i);
                    } else{
                        this.errorsF = this.constants._ERROR001;
                    }
                } else{
                    this.errorsF = this.constants._ERROR001;
                }
            }catch (Exception e){
                toast(e.getMessage().toString());
            }
        }
        if(!(this.errorsF.equals("")))
            toast(errorsF);
    }

    private void toast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
