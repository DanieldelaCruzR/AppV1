package com.udea.santiagoceron.appv0;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

public class NavDrawActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GoogleApiClient mGoogleApiClient;
    private String RegMail, RegPass,RegUsername, RegFoto,monitor;
    private TextView NDname, NDemail;
    private ImageView NDfoto;
    private boolean vista;
    private LinearLayout Lineartabs,Linearbottom,Linearbottom2;
    private NavDrawActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_draw);
        //_____________________NAVDRAW____________________________________
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Lineartabs= (LinearLayout) findViewById(R.id.Lineartabs);
        Linearbottom= (LinearLayout) findViewById(R.id.Linearbottom);
        Linearbottom2= (LinearLayout) findViewById(R.id.Linearbottom2);
        NDname=(TextView)findViewById(R.id.NDname);
        NDemail=(TextView)findViewById(R.id.NDemail);
        NDfoto=(ImageView)findViewById(R.id.NDfoto);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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




        SharedPreferences sharedPrefs = getSharedPreferences("ArchivoSP", TabsActivity.MODE_PRIVATE);
        vista=sharedPrefs.getBoolean("vista",false);
        if(vista) {
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.getMenu().getItem(0).setChecked(true);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            TiendasFragment fragmentTienda = new TiendasFragment();
            transaction.replace(R.id.content_frame, fragmentTienda).commit();
            Linearbottom.setVisibility(View.VISIBLE);
            Lineartabs.setVisibility(View.GONE);
            Linearbottom2.setVisibility(View.GONE);
        }
        else{  // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new NavDrawActivity.SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);

            Linearbottom.setVisibility(View.GONE);
            Lineartabs.setVisibility(View.VISIBLE);
            Linearbottom2.setVisibility(View.GONE);}


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_draw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.sPerfil:
                FragmentManager fm= getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                Linearbottom.setVisibility(View.GONE);
                Lineartabs.setVisibility(View.GONE);
                Linearbottom2.setVisibility(View.VISIBLE);
                PerfilFragment fragment= new PerfilFragment();
                ft.replace(R.id.content_frame2, fragment).commit();
                break;
            case R.id.sVista:
                switchVista();
                break;
            case R.id.sClose:
                cerrarSesion();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchVista() {
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
        Intent intent2 =new Intent (NavDrawActivity.this , NavDrawActivity.class);
        startActivity(intent2);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        Linearbottom.setVisibility(View.VISIBLE);
        Lineartabs.setVisibility(View.GONE);

        if (id == R.id.nav_inicio) {
            Intent intent2 =new Intent (NavDrawActivity.this , NavDrawActivity.class);
            startActivity(intent2);
            finish();
        }
        else if (id == R.id.nav_perfil) {
            Linearbottom.setVisibility(View.GONE);
            Lineartabs.setVisibility(View.GONE);
            Linearbottom2.setVisibility(View.VISIBLE);
            PerfilFragment fragment= new PerfilFragment();
            ft.replace(R.id.content_frame2, fragment).commit();

        } else if (id == R.id.nav_informacion) {
            Linearbottom.setVisibility(View.GONE);
            Lineartabs.setVisibility(View.GONE);
            Linearbottom2.setVisibility(View.VISIBLE);
            InformacionFragment fragment3= new InformacionFragment();
            ft.replace(R.id.content_frame2, fragment3).commit();
        }
        else if (id == R.id.nav_vista) {
            switchVista();
        } else if (id == R.id.nav_close) {
            cerrarSesion();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
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
    private void cerrarSesion(){
        Intent intent = new Intent(NavDrawActivity.this,activity_login.class);

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
    }

}
