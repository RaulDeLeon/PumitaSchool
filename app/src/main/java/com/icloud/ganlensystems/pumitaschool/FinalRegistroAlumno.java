package com.icloud.ganlensystems.pumitaschool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FinalRegistroAlumno extends AppCompatActivity {
    public static  final String TAG="FIREBASE";

    public  UserAdapter myAdapter;
    public EditText nombreshow, carrerashow, edadshow, generoshow;
    Button buttonAdd;
    public ProgressBar myProgressBar;
    DatabaseReference databaseReference;
    SwipeDismissDialog swipeDismissDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_registro_alumno);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        // creating layout
        creatingLayouts();
    }
    public void creatingLayouts(){
        myProgressBar=(ProgressBar) findViewById(R.id.loader);
        nombreshow = (EditText) findViewById(R.id.nombreEditText);
        carrerashow=(EditText) findViewById(R.id.carreraEditText);
        edadshow=(EditText) findViewById(R.id.edadEditText);
        generoshow = (EditText) findViewById(R.id.generoEditText);
        buttonAdd = (Button) findViewById(R.id.addButton);


    }

    //add new user to database
    public void btnAddOnClick(View v) {

        String name = nombreshow.getText().toString().trim();
        String carrera=carrerashow.getText().toString().trim();
        Double edad=Double.parseDouble(edadshow.getText().toString().trim());
        String genero = generoshow.getText().toString().trim();
        User user= new User(name, carrera, edad,genero);

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Este campo no puede estar vacío",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Double.isNaN(edad))
        {
            Toast.makeText(getApplicationContext(), "Este campo no puede estar vacío",
                    Toast.LENGTH_SHORT).show();
            return;        }

        if (TextUtils.isEmpty(carrera)) {
            Toast.makeText(getApplicationContext(), "Este campo no puede estar vacío",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(genero)) {
            Toast.makeText(getApplicationContext(), "Este campo no puede estar vacío",
                    Toast.LENGTH_SHORT).show();
        }


        updateDatabase(user);
        AlertDialog alertDialog = new AlertDialog.Builder(FinalRegistroAlumno.this).create();
        alertDialog.setTitle("Univerdad Autónoma De México");
        alertDialog.setMessage("Te damos la más coordial bienvenida esperemos qué está app sea de tú agrado");
        alertDialog.setIcon(R.drawable.ic_check_circle_green_500_24dp);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(FinalRegistroAlumno.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        alertDialog.show();




    }


    // adding new user to end  the user using on firebase database
    public void updateDatabase(User user){

        databaseReference.child("users").push().setValue(user);

        nombreshow.setText(null);
        carrerashow.setText(null);
        edadshow.setText(null);
        generoshow.setText(null);

        updateAdapter();

    }


    //update adapter
    public void updateAdapter(){

        final List<User> listUsers= new ArrayList<>();
        databaseReference.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listUsers.add(dataSnapshot.getValue(User.class));
                //displayUsers(listUsers);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Canceled",Toast.LENGTH_SHORT).show();
            }
        });


    }

    /*
       //display the user on Adapter
       //public void displayUsers(List<User> ls){
           myProgressBar.setVisibility(View.GONE);
           editTextName.setText(null);
           editTextCountry.setText(null);
           editTextEmail.setText(null);
           myAdapter.setData(ls);
           myAdapter.notifyDataSetChanged();
       }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




}
