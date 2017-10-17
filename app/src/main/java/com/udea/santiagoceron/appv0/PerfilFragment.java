package com.udea.santiagoceron.appv0;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    private TextView sPass,sMail,sUsername,sCelular;
    private String RegMail,RegPass,RegUsername,RegPhoto,RegCelular;
    private ImageView fotoPerfil;



    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_perfil,container,false);


        sPass = (TextView) view.findViewById(R.id.sPass);
        sMail = (TextView) view.findViewById(R.id.sEmail);
        sUsername = (TextView)view.findViewById(R.id.sUsername);
        fotoPerfil = (ImageView) view.findViewById(R.id.fotoPerfil);
        sCelular=(TextView) view.findViewById(R.id.sCelular);


        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("ArchivoSP",activity_registro.MODE_PRIVATE);
        String noAvailabel = "No Avariable";
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



        return view;


    }

    private void loadImageFromURL(String url, ImageView imageView) {
        Picasso.with(this.getActivity()).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess(){}
                    @Override
                    public void onError(){}
                });
    }



}
