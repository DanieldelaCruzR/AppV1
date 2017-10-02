package com.udea.santiagoceron.appv0;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity {

    private String RegMail, RegPass,RegUsername, RegFoto;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),"Error en login", Toast.LENGTH_SHORT).show();
                    }
                }/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //REcuperar lo de reg
        /*
        Bundle extras = getIntent().getExtras();
        RegMail = extras.getString("mail");
        RegPass = extras.getString("pass");
        RegUsername = extras.getString("username");
        RegFoto= extras.getString("foto");


        Toast.makeText(this,RegMail, Toast.LENGTH_SHORT).show();
        Toast.makeText(this,RegPass,Toast.LENGTH_SHORT).show();*/
    }

    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //use menu!!!!!
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.sPerfil:
                intent = new Intent(MainActivity.this,activity_perfil.class);
                /*intent.putExtra("pass",RegPass);
                intent.putExtra("mail",RegMail);
                intent.putExtra("username",RegUsername);
                intent.putExtra("foto",RegFoto);*/
                startActivity(intent);
                break;
            case R.id.sClose:
                intent = new Intent(MainActivity.this,activity_login.class);
                String vacio = " ";
                SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", this.MODE_PRIVATE);
                SharedPreferences.Editor editorSP= sharedPrefs.edit();
                editorSP.putInt("optLog",0);
                editorSP.putString("username",vacio);
                editorSP.putString("email",vacio);
                editorSP.putString("celular",vacio);
                editorSP.putString("password",vacio);
                editorSP.putString("foto",vacio);
                editorSP.commit();
                /*intent.putExtra("pass",RegPass);
                intent.putExtra("mail",RegMail);*/
                intent.putExtra("Splash",false);
                startActivity(intent);
                signOut();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });
    }
}
