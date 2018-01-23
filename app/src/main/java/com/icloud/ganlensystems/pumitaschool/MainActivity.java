package com.icloud.ganlensystems.pumitaschool;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.icloud.ganlensystems.pumitaschool.Objects.AdapterPosts;
import com.icloud.ganlensystems.pumitaschool.Objects.Posts;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nombretxt1;
    private TextView emailperfil;
    private NavigationView nvDrawer;

    private TextView infoTextView;
    ////////////////////7

    // variable for login fb
    private CallbackManager callbackManager;
    private List<String> permissions;

    // variables for news
    List<Posts> ListPosts;
    RecyclerView recyclerView;
    private AdapterPosts adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();


        if(AccessToken.getCurrentAccessToken() == null) {
            alertDialogForLoginFb();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm=getFragmentManager();
        fm.beginTransaction().replace(R.id.contenido_frame,new MainFragment()).commit();

        if(AccessToken.getCurrentAccessToken() != null) {
            getNews();
        }

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
    // Aumenta el menú; esto agrega elementos a la barra de acción si está presente.
        getMenuInflater().inflate(R.menu.main, menu);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            nombretxt1 = findViewById(R.id.nombretxt);
            emailperfil = findViewById(R.id.tvEmailProfile);
            String nombre = user.getDisplayName();
            nombretxt1.setText(nombre);
            String email = user.getEmail();
            emailperfil.setText(email);

        }else {
            goLoginScreen();
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    // Maneja clics del elemento de la barra de acción aquí. La barra de acción
        // maneja automáticamente los clics en el botón Inicio / Arriba, tan largo

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cerrrarsesion) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            goLoginScreen();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Maneja la acción de la cámara

        int id = item.getItemId();

        if (id == R.id.Noticias) {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
        } else if (id == R.id.Negocios) {
           Intent i = new Intent(MainActivity.this, MapsActivity.class);
           startActivity(i);

        }

        else if (id == R.id.UNAMOffline) {
            Intent i = new Intent(MainActivity.this, unam_offline.class);
            startActivity(i);
        }


        else if (id == R.id.Mapa) {
            if(AccessToken.getCurrentAccessToken() != null) {
                ListPosts = new ArrayList<Posts>();
                adapter = new AdapterPosts(ListPosts);
                recyclerView.setAdapter(adapter);
            }
            fragment = new MapaCU();

        } else if (id == R.id.Agregar) {
            Intent i = new Intent(MainActivity.this, WS_Negocio.class);
            startActivity(i);
        } else if (id == R.id.Promociones) {
            Intent i = new Intent(MainActivity.this, Image_List.class);
            startActivity(i);

        } else if (id == R.id.Acerca) {
            //Intent i = new Intent(MainActivity.this, Fragment_Contacto.class);
            //startActivity(i);
            if(AccessToken.getCurrentAccessToken() != null) {
                ListPosts = new ArrayList<Posts>();
                adapter = new AdapterPosts(ListPosts);
                recyclerView.setAdapter(adapter);
            }
            fragment = new Fragment_Contacto();



        }else if (id == R.id.CerrarSesion) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            goLoginScreen();
        }
        if (fragment!= null){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.contenido_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void goLoginScreen() {


        Intent intent = new Intent(this, bienvenido.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    public void alertDialogForLoginFb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Alerta");
        builder.setMessage("Necesitas iniciar sesión en facebook para poder usar La sección de noticias.");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                permissions = Arrays.asList("public_profile");
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, permissions);
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getNews();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "Inicio de Facebook cancelado", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(MainActivity.this, "Error al inciar facebook", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.show();
    }

    public void getNews(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_news);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ListPosts = new ArrayList<Posts>();

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "UNAM.MX.Oficial/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        String enumPost = null;
                        TextView postDescription = (TextView) findViewById(R.id.post_description);

                        Log.i("fbData", graphResponse.getJSONObject().toString());
                        Log.i("responseFb", graphResponse.getRawResponse().toString());

                        try {
                            JSONObject json = graphResponse.getJSONObject();
                            JSONArray jarray = json.getJSONArray("data");
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject onePost= jarray.getJSONObject(i);

                                enumPost += onePost.getString("message")+"\n";
                                if(onePost.has("full_picture")) {
                                    enumPost += onePost.getString("full_picture") + "\n";
                                }
                                if(onePost.has("full_picture")) {
                                    ListPosts.add(new Posts(onePost.getString("message"),
                                            onePost.getString("full_picture")));
                                }else{
                                    ListPosts.add(new Posts(onePost.getString("message"), ""));
                                }


                            }
                            //

                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        TextView post = (TextView) findViewById(R.id.news);
                        post.setText("\n\n\n");
                    }
                });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "full_picture,id,created_time,message,permalink_url,object_id");
        parameters.putString("limit", "50");
        request.setParameters(parameters);
        request.executeAsync();
        adapter = new AdapterPosts(ListPosts);
        recyclerView.setAdapter(adapter);
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
