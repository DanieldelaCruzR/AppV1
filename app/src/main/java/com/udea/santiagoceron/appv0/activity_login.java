package com.udea.santiagoceron.appv0;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class activity_login extends AppCompatActivity {

private String RegMail,RegPass,RegCelular,RegUsername,uriFoto;
    private String celular, mail,password, username;
    private Boolean Splash;
    private EditText eMail,ePass;
    private int optLog;
    private String urlDefault="https://image.freepik.com/iconos-gratis/perfil-macho-sombra-de-usuario_318-40244.jpg";
    GoogleApiClient mGoogleApiClient;
    GoogleSignInAccount acction;

    Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //LOGIN con GOOGLE

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

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        //Fin LOGIN GOOGLE

        //Declaracion de variables
        eMail = (EditText) findViewById(R.id.eEmail);
        ePass = (EditText) findViewById(R.id.ePass);

        Bundle extras = getIntent().getExtras();


        //Si es splash quien llama obvia las ultimas lineas de codigo
        Splash = extras.getBoolean("Splash");
        if (Splash) return;


        RegMail = extras.getString("mail");
        RegPass = extras.getString("pass");
        RegCelular= extras.getString("celular");
        RegUsername=extras.getString("username");

    }

    // Boton de registro
    public void bRegister(View view){
        Intent intent = new Intent(activity_login.this,activity_registro.class);
        startActivityForResult(intent,111); //distinguir quien me llamo
    }

    //Boton de login Normal
    public void bLogin(View view){
        optLog=1;
        SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", activity_login.MODE_PRIVATE);
        SharedPreferences.Editor editorSP= sharedPrefs.edit();
        editorSP.putInt("optLog",optLog);
        editorSP.commit();
        goMainActivity();
    }


    //Funcion de LOGIN GOOGLE
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 222);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 111 && resultCode == RESULT_OK){
            RegMail = data.getExtras().getString("mail");
            RegPass = data.getExtras().getString("pass");
            RegCelular = data.getExtras().getString("celular");
            RegUsername = data.getExtras().getString("username");
            Toast.makeText(this,RegMail,Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == 222) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("GOOGLE", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acction = result.getSignInAccount();
            Toast.makeText(getApplicationContext(),acction.getDisplayName(), Toast.LENGTH_SHORT).show();
            optLog=2;
            SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", activity_login.MODE_PRIVATE);
            SharedPreferences.Editor editorSP= sharedPrefs.edit();
            editorSP.putInt("optLog",optLog);
            editorSP.commit();
            goMainActivity();

        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    private void goMainActivity() {
        //Funcion que administra datos a enviar y el tipo de logeo

        Intent intent = new Intent(activity_login.this,MainActivity.class);
        if(optLog==0){}
        else if(optLog==1){
            mail = eMail.getText().toString();
            password = ePass.getText().toString();

            if(!(mail.equals(RegMail))) return;
            if (!(password.equals(RegPass))) return;

            SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", activity_registro.MODE_PRIVATE);
            SharedPreferences.Editor editorSP= sharedPrefs.edit();
            editorSP.putString("username",RegUsername);
            editorSP.putString("email",RegMail);
            editorSP.putString("celular",RegCelular);
            editorSP.putString("password",RegPass);
            editorSP.putString("foto",urlDefault);
            editorSP.commit();
        }
        else if(optLog==2){
            String personName = acction.getDisplayName();
            String personEmail = acction.getEmail();
            Uri personPhoto = acction.getPhotoUrl();
            if(personPhoto==null){
                uriFoto=urlDefault;
            }
            else
                uriFoto=personPhoto.toString();

            SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", activity_registro.MODE_PRIVATE);
            SharedPreferences.Editor editorSP= sharedPrefs.edit();
            editorSP.putString("username",personName);
            editorSP.putString("email",personEmail);
            editorSP.putString("celular","Number phone no available");
            editorSP.putString("password","Password no available");
            editorSP.putString("foto",uriFoto);
            editorSP.commit();


            /*intent.putExtra("mail", personEmail);
            intent.putExtra("username", personName);
            intent.putExtra("pass", password);
            intent.putExtra("foto", uriFoto);*/
        }
        else if(optLog==3){}
        startActivity(intent);
        finish();

    }
}
