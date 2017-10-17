package com.udea.santiagoceron.appv0;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class TabsActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private String RegMail, RegPass,RegUsername, RegFoto,monitor;
    private boolean vista;
    private LinearLayout Lineartabs,Linearbottom;
    GoogleApiClient mGoogleApiClient;

    //BOTONES


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                    TiendasFragment fragmentTienda = new TiendasFragment();
                    transaction1.replace(R.id.content_frame, fragmentTienda).commit();
                    return true;
                case R.id.navigation_dashboard:
                    FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                    PromocionesFragment fragmentPromociones = new PromocionesFragment();
                    transaction2.replace(R.id.content_frame, fragmentPromociones).commit();
                    return true;
                case R.id.navigation_notifications:
                    FragmentTransaction transaction3 = fragmentManager.beginTransaction();
                    CarritoFragment fragmentCarrito = new CarritoFragment();
                    transaction3.replace(R.id.content_frame, fragmentCarrito).commit();
                    return true;
            }
            return false;
        }

    };

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TABS
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        Lineartabs= (LinearLayout) findViewById(R.id.Lineartabs);
        Linearbottom= (LinearLayout) findViewById(R.id.Linearbottom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", TabsActivity.MODE_PRIVATE);
        vista=sharedPrefs.getBoolean("vista",false);


        if(vista){
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.getMenu().getItem(0).setChecked(true);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            TiendasFragment fragmentTienda = new TiendasFragment();
            transaction.replace(R.id.content_frame, fragmentTienda).commit();
            Linearbottom.setVisibility(View.VISIBLE);
            Lineartabs.setVisibility(View.GONE);
        }
        else {

            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);

            Linearbottom.setVisibility(View.GONE);
            Lineartabs.setVisibility(View.VISIBLE);
        }


        //Google

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

        //fin

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {

            case R.id.sPerfil:
                intent = new Intent(TabsActivity.this,activity_perfil.class);
                startActivity(intent);
                break;
            case R.id.sVista:
                SharedPreferences sharedPrefs2 = getSharedPreferences("ArchivoSP", this.MODE_PRIVATE);
                SharedPreferences.Editor editorSP2 = sharedPrefs2.edit();
                vista=sharedPrefs2.getBoolean("vista",true);
                if(vista){
                    editorSP2.putBoolean("vista",false);
                    editorSP2.commit();
                    monitor="boton";
                }
                else {
                    editorSP2.putBoolean("vista",true);
                    editorSP2.commit();
                    monitor="tabs";
                }
                Toast.makeText(getApplicationContext(),monitor, Toast.LENGTH_SHORT).show();
                Intent intent2 =new Intent (TabsActivity.this , TabsActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.sClose:

                intent = new Intent(TabsActivity.this,activity_login.class);

                SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", this.MODE_PRIVATE);
                SharedPreferences.Editor editorSP= sharedPrefs.edit();

                if(sharedPrefs.getInt("optLog",0)==3){
                    LoginManager.getInstance().logOut();
                }
                if(sharedPrefs.getInt("optLog",0)==2){
                    signOut();
                }
                editorSP.putInt("optLog",0);
                editorSP.putString("username",RegUsername);
                editorSP.putString("email",RegMail);
                editorSP.putString("password",RegPass);
                //editorSP.putString("password",null);
                //editorSP.putString("foto",null);
                editorSP.commit();
                intent.putExtra("pass",RegPass);
                intent.putExtra("mail",RegMail);
                intent.putExtra("Splash",false);

                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", TabsActivity.MODE_PRIVATE);
            vista=sharedPrefs.getBoolean("vista",true);
            if(vista){
                Linearbottom.setVisibility(View.VISIBLE);
                Lineartabs.setVisibility(View.GONE);
            }

            switch (position){
                case 0: return new TiendasFragment();
                case 1: return new PromocionesFragment();
                case 2: return new CarritoFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TIENDAS";
                case 1:
                    return "PROMOCIONES";
                case 2:
                    return "CARRITO";
            }
            return null;
        }
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
