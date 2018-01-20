package com.icloud.ganlensystems.pumitaschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

public class registro_alumno_inicio extends AppCompatActivity {
    private EditText txtEmailRegistroAlumno, txtPwdRegistroAlumno;
    private TextView RDL_ShowWelcome;
    private FirebaseAuth firebaseAuth;
    private Typeface pacifico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alumno_inicio);

        String fuente1 = "fuentes/pacifico.ttf";
        this.pacifico = Typeface.DEFAULT.createFromAsset(getAssets(), fuente1);
        txtEmailRegistroAlumno = (EditText) findViewById(R.id.ET_EmailAlumnoRegistro);
        RDL_ShowWelcome = findViewById(R.id.TV_ShowWelcome);
        RDL_ShowWelcome.setTypeface(pacifico);
        txtPwdRegistroAlumno = (EditText) findViewById(R.id.ET_PassAlumnoRegistro);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void BTN_Registro_Alumno_Click(View v) {


        String email = txtEmailRegistroAlumno.getText().toString().trim();
        String password = txtPwdRegistroAlumno.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            txtEmailRegistroAlumno.setError("Este campo no puede estar vacío");
            return;

        }
        if (TextUtils.isEmpty(password)) {
            txtPwdRegistroAlumno.setError("Este campo no puede estar vacío");
            return;

        }
        final ProgressDialog progressDialog = ProgressDialog.show(registro_alumno_inicio.this, "Por favor espera", "Procesando...", true);
        (firebaseAuth.createUserWithEmailAndPassword(txtEmailRegistroAlumno.getText().toString(), txtPwdRegistroAlumno.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(registro_alumno_inicio.this, "Registro Exitoso, Continua.", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(registro_alumno_inicio.this, FinalRegistroAlumno.class);
                            startActivity(i);

                            finish(); //Elimina de la pila de Actividades.

                        }

                        else
                        { String error = "";

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int backalumnoregis = item.getItemId();

        if (backalumnoregis == android.R.id.home);{

            Intent intent = new Intent(registro_alumno_inicio.this, bienvenido.class);
            startActivity(intent);
            finish(); //Elimina de la pila de Actividades.
        }
        return super.onOptionsItemSelected(item);



    }

}