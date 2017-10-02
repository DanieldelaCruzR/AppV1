package com.udea.santiagoceron.appv0;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class activity_registro extends AppCompatActivity {
    public EditText ePass, ePass2,eMail, eUsername,eCelular;
    String pass1,pass2,mail, username,celular;
    String No_eq,No_valid,No_Fill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ePass = (EditText) findViewById(R.id.ePass);
        ePass2 = (EditText) findViewById(R.id.ePass2);
        eMail = (EditText) findViewById(R.id.eEmail);
        eUsername = (EditText)findViewById(R.id.eUsername);
        eCelular= (EditText)findViewById(R.id.eCelular);
        //err messages
        No_eq = "Pass did not match";
        No_valid = "Enter a valid email";
        No_Fill = "Please fill Password";

    }

    public void bRegister(View view){

        //get data
        pass1 = ePass.getText().toString();
        pass2 = ePass2.getText().toString();
        mail = eMail.getText().toString();
        username = eUsername.getText().toString();
        celular=eCelular.getText().toString();


        //check pass

        if(pass1.isEmpty()) {
            ePass.setError(No_Fill);
            return;
        }else {
            ePass.setError(null);
        }if(pass2.isEmpty()) {
            ePass2.setError(No_Fill);
            return;
        }else{
            ePass2.setError(null);
        }

        if (!(pass2.equals(pass1))) {
            ePass2.setError(No_eq);
            return;
        }else{
            ePass2.setError(null);
        }


        //check email
        if (!(mail.contains("@") && mail.contains("."))){
            eMail.setError(No_valid);
            return;
        }else{
            eMail.setError(null);
        }

        //volver a la app



        Intent intent = new Intent();
        intent.putExtra("mail", mail);
        intent.putExtra("pass",pass1);
        intent.putExtra("username",username);
        intent.putExtra("celular",celular);
        setResult(RESULT_OK, intent);
        finish();
    }



    // va registrar __-


}
