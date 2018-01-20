package com.icloud.ganlensystems.pumitaschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class registro_alumno_final extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private ListView list_data;
    private DatabaseReference mDatabaseReference;
    private EditText Alumno_NC, Alumno_Carrera, Alumno_Facultad, Alumno_Edad, Alumno_Genero;
    private Button Alumno_RegistroFinal;
    Firebase firebase;
    private List<Registro_Alumno> lstAlumno_detalles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alumno_final);
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://pumitascool.firebaseio.com");
        Alumno_NC = findViewById(R.id.ET_NombreComAlumno);
        Alumno_Carrera = findViewById(R.id.ET_Carrera);
        Alumno_Facultad = findViewById(R.id.ET_Facultad);
        Alumno_Edad = findViewById(R.id.ET_Edad);
        Alumno_Genero = findViewById(R.id.ET_Genero);
        Alumno_RegistroFinal = findViewById(R.id.BTN_RegistroAlumno);
        //Firebase
        inicioFirebase();
        addEventFirebaseListener();

    }

    private void addEventFirebaseListener() {
        mDatabaseReference.child("Detalle_Alumnos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            if (lstAlumno_detalles.size() > 0)
                lstAlumno_detalles.clear();
            for (DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                Registro_Alumno registro_alumno = postSnapShot.getValue(Registro_Alumno.class);
                lstAlumno_detalles.add(registro_alumno);
            }
            ListViewAlumnos adapter = new ListViewAlumnos(registro_alumno_final.this, lstAlumno_detalles);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicioFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    public void BTNFinalAlumni(View view) {
        String alumno_NC = Alumno_NC.getText().toString().trim();
        String alumno_Carrera = Alumno_Carrera.getText().toString().trim();
        String alumno_Facultad = Alumno_Facultad.getText().toString().trim();
        String alumno_Edad = Alumno_Edad.getText().toString().trim();
        String alumno_Genero = Alumno_Genero.getText().toString().trim();



        if (TextUtils.isEmpty(alumno_NC)) {
            Alumno_NC.setError("Este campo no puede estar vacío");
            return;

        }
        if (TextUtils.isEmpty(alumno_Carrera)) {
            Alumno_Carrera.setError("Este campo no puede estar vacío");
            return;

        }if (TextUtils.isEmpty(alumno_Facultad)) {
            Alumno_Facultad.setError("Este campo no puede estar vacío");
            return;

        }if (TextUtils.isEmpty(alumno_Edad)) {
            Alumno_Edad.setError("Este campo no puede estar vacío");
            return;

        }if (TextUtils.isEmpty(alumno_Genero)) {
            Alumno_Genero.setError("Este campo no puede estar vacío");
            return;

        }
        firebase.push().setValue(alumno_NC);
        firebase.push().setValue(alumno_Carrera);
        firebase.push().setValue(alumno_Facultad);
        firebase.push().setValue(alumno_Edad);
        firebase.push().setValue(alumno_Genero);

        String uploadId = mDatabaseReference.push().getKey();
        mDatabaseReference.child(uploadId);
        final ProgressDialog progressDialog = ProgressDialog.show(registro_alumno_final.this, "Por favor espera", "Procesando...", true);
        Intent i = new Intent(registro_alumno_final.this, MainActivity.class);
        startActivity(i);


        //
        // Guarda la información de la imagen en la base de datos de firebase
        //String KEY = databaseReference.push().getKey();
       // databaseReference.child(KEY).setValue(registro_alumno);

       // Intent i = new Intent(registro_alumno_final.this, MainActivity.class);
       // startActivity(i);

    }
}
