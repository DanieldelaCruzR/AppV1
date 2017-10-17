package com.udea.santiagoceron.appv0;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.util.Timer;
import java.util.TimerTask;

public class activity_splash extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2800;
    int optLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//Configuraciones de la pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // SIN TITULO
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);      //FULLSCREEN
        setContentView(R.layout.activity_splash);


        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent;
                SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", activity_splash.MODE_PRIVATE);
                //SharedPreferences.Editor editorSP= sharedPrefs.edit();
                optLog = sharedPrefs.getInt("optLog",0);

                if(optLog==0){
                    intent = new Intent(activity_splash.this,activity_login.class);
                    intent.putExtra("Splash",true);
                }/*
                else if (AccessToken.getCurrentAccessToken()!=null){
                    intent = new Intent(activity_splash.this,MainActivity.class);
                }*/
                else {
                    intent = new Intent(activity_splash.this,NavDrawActivity.class); //Cambio a tabsactivity
                }
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,SPLASH_DELAY);
    }
}
