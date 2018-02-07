package com.icloud.ganlensystems.pumitaschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class bienvenido extends AppCompatActivity {
    private EditText txtEmailIngresaAlumno, txtpwdAlumno;
    private TextView txtRecuperarContraseña, txtRegistroAlumno;
    private TextView TV_BienvenidaLLB, txtpumitascoolfnt;
    private Typeface helvetica, pacifico;
    private Button Ingresa_Alumno_Firebase, TestBTN;
    private FirebaseAuth firebaseAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private LoginButton IngresarFacebook;
    private CallbackManager callbackManager;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ProgressBar progressBar;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

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
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_bienvenido);
        progressBar = findViewById(R.id.progressBar);
        ////////////////////////////////////////////777////////////////////////////////////7////////////FACEBOOK
        callbackManager = CallbackManager.Factory.create();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    goMainScreen();
                }
            }
        };

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        String fuente1 = "fuentes/pacifico.ttf";
        this.pacifico = Typeface.DEFAULT.createFromAsset(getAssets(), fuente1);
        String fuente = "fuentes/helvetica.ttf";
        this.helvetica = Typeface.createFromAsset(getAssets(), fuente);
        txtpumitascoolfnt =  findViewById(R.id.tv_pumitascool_letter);
        txtpumitascoolfnt.setTypeface(pacifico);
        TV_BienvenidaLLB = findViewById(R.id.TV_IngresaBienvenida);
        TV_BienvenidaLLB.setTypeface(helvetica);
        txtEmailIngresaAlumno =  findViewById(R.id.ET_EmailAlumno);
        txtEmailIngresaAlumno.setTypeface(helvetica);
        txtpwdAlumno = (EditText) findViewById(R.id.ET_PassAlumno);
        //////////////////////////////////////////////// txtpwdAlumno.setError("La contraseña es demasiado corta");
        txtpwdAlumno.setTypeface(helvetica);
        Ingresa_Alumno_Firebase = findViewById(R.id.BTN_IngresarAlumno);
        Ingresa_Alumno_Firebase.setTypeface(helvetica);
        txtRegistroAlumno = findViewById(R.id.TV_Registro_Alumno);
        txtRegistroAlumno.setTypeface(helvetica);
        txtRecuperarContraseña = findViewById(R.id.TV_RecuperarContraseña);
        txtRecuperarContraseña.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bienvenido.this, recuperar_password.class);
                startActivity(intent);
                //Elimina de la pila de Actividades.

            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        txtRegistroAlumno.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bienvenido.this, registro_alumno_inicio.class);
                startActivity(intent);
                //    finish();Elimina de la pila de Actividades.

            }
        });

    }


    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);

    }


    protected void OnStop(){
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);


    }

    private void goMainScreen() {


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void BTN_Ingresa_Alumno_Click(View v) {
        String email = txtEmailIngresaAlumno.getText().toString().trim();
        String password = txtpwdAlumno.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            txtEmailIngresaAlumno.setError("Este campo no puede estar vacío");
            return;

        }
        if (TextUtils.isEmpty(password)) {
            txtpwdAlumno.setError("Este campo no puede estar vacío");
            return;

        }
        final ProgressDialog progressDialog = ProgressDialog.show(bienvenido.this, "Por favor espera", "Procesando...", true);

        (firebaseAuth.signInWithEmailAndPassword(txtEmailIngresaAlumno.getText().toString(), txtpwdAlumno.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();


                        if (task.isSuccessful()) {
                            Toast.makeText(bienvenido.this, "Ingresaste correctamente", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(bienvenido.this, MainActivity.class);
                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                            startActivity(i);
                        }


                        else {

                            String error = "";

                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                error = "Contraseña Muy Corta";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                error = "Contraseña Incorrecta";

                            } catch (FirebaseAuthInvalidUserException e) {
                                error = "Email Inválido";

                            } catch (FirebaseAuthUserCollisionException e) {
                                error = "Esta cuenta Ya Existe!";
                            } catch (Exception e) {
                                error = "Error Desconocido, Intenta Nuevamente";
                                e.printStackTrace();

                            }

                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();



                        }

                    }





                });

    }

}