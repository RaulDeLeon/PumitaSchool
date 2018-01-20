package com.icloud.ganlensystems.pumitaschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class recuperar_password extends AppCompatActivity {
    private EditText inputRecuperar;
    private Button BTN_Recuperar;
    private TextView TVRPass;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Typeface pacifico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);
        String fuente = "fuentes/pacifico.ttf";
        this.pacifico = Typeface.createFromAsset(getAssets(), fuente);
        TVRPass = (TextView) findViewById(R.id.TV_Recuperar);
        TVRPass.setTypeface(pacifico);
        inputRecuperar = (EditText) findViewById(R.id.ET_Recuperar);
        BTN_Recuperar = (Button) findViewById(R.id.BTN_Recuperar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        BTN_Recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        BTN_Recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(recuperar_password.this, "Por favor espera", "Procesando...", true);
                progressDialog.dismiss();
                String email = inputRecuperar.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Ingresa tu correo electrónico", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(recuperar_password.this, "¡Te hemos enviado instrucciones para restablecer su contraseña a tu correo electrónico!\n", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(recuperar_password.this, bienvenido.class);
                                    startActivity(intent);
                                    finish(); //Elimina de la pila de Actividades.
                                } else {
                                    Toast.makeText(recuperar_password.this, "Error al enviar el correo electrónico", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

    }

}

