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


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class activity_login extends AppCompatActivity {

    private String RegMail,RegPass,RegCelular,RegUsername,uriFoto;
    private String celular, mail,password, username;
    private EditText eMail,ePass;
    private int optLog;
    private boolean splash;
    private String urlDefault="https://image.freepik.com/iconos-gratis/perfil-macho-sombra-de-usuario_318-40244.jpg";
    GoogleApiClient mGoogleApiClient;
    GoogleSignInAccount acction;


    private LoginButton FbLogin;       //facebook LogIn
    private CallbackManager callbackManager;  //fb


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", activity_login.MODE_PRIVATE);
        SharedPreferences.Editor editorSP = sharedPrefs.edit();

//LOGIN con GOOGLE

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
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

//Login Facebook
        callbackManager = CallbackManager.Factory.create();
        FbLogin = (LoginButton) findViewById(R.id.login_button);
        FbLogin.setReadPermissions(Arrays.asList("email"));

        FbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                RequestData();
            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Cacncelado FB", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Error FB", Toast.LENGTH_SHORT).show();

            }
        });

    //fin login facebook

    //Declaracion de variables
        eMail = (EditText) findViewById(R.id.eEmail);
        ePass = (EditText) findViewById(R.id.ePass);

        Bundle extras = getIntent().getExtras();


        //Si es splash quien llama obvia las ultimas lineas de codigo
        //if(splash) return;

        //RegMail = extras.getString("mail");
        //RegPass = extras.getString("pass");
        //Toast.makeText(this,RegMail,Toast.LENGTH_SHORT).show();
        //RegCelular= extras.getString("celular");
        //RegUsername=extras.getString("username");


    }

    // Boton de registro
    public void bRegister(View view){
        Intent intent = new Intent(activity_login.this,activity_registro.class);
        startActivityForResult(intent,111); //distinguir quien me llamo
    }





    //Boton de login Normal
    public void bLogin(View view){
        optLog=1;
        SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", activity_splash.MODE_PRIVATE);
        RegMail = sharedPrefs.getString("email","0");
        RegPass = sharedPrefs.getString("password","0");
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
            //obtener con intent data
            RegMail = data.getExtras().getString("mail");
            RegPass = data.getExtras().getString("pass");
            RegCelular = data.getExtras().getString("celular");
            RegUsername = data.getExtras().getString("username");
            //Toast.makeText(this,RegMail,Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == 222) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else {
            callbackManager.onActivityResult(requestCode,resultCode,data);
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

        Intent intent = new Intent(activity_login.this,NavDrawActivity.class);
        if(optLog==0){}
        else if(optLog==1){
            mail = eMail.getText().toString();
            password = ePass.getText().toString();

            if(!(mail.equals(RegMail))){
                eMail.setError("wrong mail");
                return;
            }
            if (!(password.equals(RegPass))) {
                    ePass.setError("wrong pass");
                return;
            }

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
        else if(optLog==3){

        }
        startActivity(intent);
        finish();

    }

    private void RequestData() {

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                final JSONObject json = response.getJSONObject();

                try {
                    if(json != null){

                        RegMail = object.getString("email");
                        // birthday = object.getString("birthday");
                        //String gender = object.getString("gender");
                        RegUsername = object.getString("name");
                        //String id = object.getString("id");
                        uriFoto = object.getJSONObject("picture").getJSONObject("data").getString("url");

                      //  Toast.makeText(this,uriFoto,Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", activity_registro.MODE_PRIVATE);
                        SharedPreferences.Editor editorSP= sharedPrefs.edit();
                        editorSP.putString("username",RegUsername);
                        editorSP.putString("email",RegMail);
                        editorSP.putInt("optLog",3);
                        editorSP.putString("celular","Number phone no available");
                        editorSP.putString("password","Password no available");
                        editorSP.putString("foto",uriFoto);
                        editorSP.commit();
                        goMainActivity();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
