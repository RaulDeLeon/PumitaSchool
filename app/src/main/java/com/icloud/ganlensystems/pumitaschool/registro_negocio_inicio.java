package com.icloud.ganlensystems.pumitaschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class registro_negocio_inicio extends AppCompatActivity {
    private EditText txtEmailRegistroNegocio, txtPwdNegocio, cellSMS_Negocio, codeSMS_Confirmacion_Negocio;
    private TextView TextNegocio;
    private FirebaseAuth firebaseAuth;
    public Typeface pacifico;
    private Typeface helvetica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_negocio_inicio);

        String fuentepacifico = "fuentes/pacifico.ttf";
        this.pacifico = Typeface.createFromAsset(getAssets(), fuentepacifico);

        String fuente = "fuentes/helvetica.ttf";
        this.helvetica = Typeface.createFromAsset(getAssets(), fuente);

        TextNegocio = (TextView) findViewById(R.id.TV_MINegocio);
        TextNegocio.setTypeface(pacifico);

        txtEmailRegistroNegocio = (EditText) findViewById(R.id.ET_EmailNegocioRegistro);
        txtEmailRegistroNegocio.setTypeface(helvetica);
        txtPwdNegocio = (EditText) findViewById(R.id.ET_PassNegocioRegistro);
        txtPwdNegocio.setTypeface(helvetica);

        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void BTN_Registro_Negocio_Click(View v) {
        final ProgressDialog progressDialog = ProgressDialog.show(registro_negocio_inicio.this, "Por favor espera", "Procesando...", true);
        (firebaseAuth.createUserWithEmailAndPassword(txtEmailRegistroNegocio.getText().toString(), txtPwdNegocio.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(registro_negocio_inicio.this, "Registro Exitoso, Por favor continua", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(registro_negocio_inicio.this, registro_negocio_final.class);
                            startActivity(i);
                            finish(); //Elimina de la pila de Actividades.

                        }

                        else
                        {
                            String error = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                error = "Contraseña muy Corta";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                error = "Email Incorrecto";

                            } catch (FirebaseAuthInvalidUserException e){
                                error = "Email Inválido";

                            } catch (FirebaseAuthUserCollisionException e) {
                                error = "Esta cuenta ya existe!";
                            } catch (Exception e) {
                                error = "Error Desconocido, Intenta Nuevamente";
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                        }
                    }
                });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int backalumnoregis = item.getItemId();

        if (backalumnoregis == android.R.id.home);{

            Intent intent = new Intent(registro_negocio_inicio.this, bienvenido.class);
            startActivity(intent);
            finish(); //Elimina de la pila de Actividades.
        }
        return super.onOptionsItemSelected(item);

    }
}
