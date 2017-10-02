package com.udea.santiagoceron.appv0;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class activity_perfil extends AppCompatActivity {
    private TextView sPass,sMail,sUsername,sCelular;
    private String RegMail,RegPass,RegUsername,RegPhoto,RegCelular;
    private ImageView fotoPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        sPass = (TextView) findViewById(R.id.sPass);
        sMail = (TextView) findViewById(R.id.sEmail);
        sUsername = (TextView)findViewById(R.id.sUsername);
        fotoPerfil = (ImageView)findViewById(R.id.fotoPerfil);
        sCelular=(TextView)findViewById(R.id.sCelular);

        SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", activity_registro.MODE_PRIVATE);
        String noAvailabel = "No Available";
        RegUsername= sharedPrefs.getString("username",noAvailabel);
        RegMail=sharedPrefs.getString("email",noAvailabel);
        RegCelular=sharedPrefs.getString("celular",noAvailabel);
        RegPass=sharedPrefs.getString("password",noAvailabel);
        RegPhoto=sharedPrefs.getString("foto",noAvailabel);

        sPass.setText(RegPass);
        sMail.setText(RegMail);
        sUsername.setText(RegUsername);
        sCelular.setText(RegCelular);
        loadImageFromURL(RegPhoto,fotoPerfil);
        /*

        Bundle extras = getIntent().getExtras();
        RegMail = extras.getString("mail");
        RegPass = extras.getString("pass");
        RegUsername = extras.getString("username");
        RegPhoto = extras.getString("foto");*/



    }

    private void loadImageFromURL(String url, ImageView imageView) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess(){}
                    @Override
                    public void onError(){}
                });
    }

}
