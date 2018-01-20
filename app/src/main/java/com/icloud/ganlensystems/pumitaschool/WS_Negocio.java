package com.icloud.ganlensystems.pumitaschool;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class WS_Negocio extends AppCompatActivity {
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ImageView imageView;
    private EditText editText;
    private Uri imguri;

    public static final String FB_Storage_Path = "imagen_negocio/";
    public static final String FB_Database_Path = "imagen_negocio";
    public static final int Request_Code = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ws__negocio);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(FB_Database_Path);

        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.txtImageName);
    }


    public void btnBrowse_Click(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una foto de tú negocio"), Request_Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @SuppressWarnings("VisibleForTests")
    public void btnUpload_Click(View v) {
        if (imguri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Cargando la imagen");
            dialog.show();

            // Obtenga la referencia de almacenamiento
            StorageReference ref = storageReference.child(FB_Storage_Path + System.currentTimeMillis() + "." + getImageExt(imguri));

            // Agregar archivo a referencia


            ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    // Descartar el diálogo cuando el éxito

                    dialog.dismiss();
                    //Display success toast msg
                    Toast.makeText(getApplicationContext(), "Imagen Cargada", Toast.LENGTH_SHORT).show();
                    ImageUpload imageUpload = new ImageUpload(editText.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                    // Guarda la información de la imagen en la base de datos de firebase
                    String uploadId = databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(imageUpload);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Guarda la información de la imagen en la base de datos de firebase

                            dialog.dismiss();
                            // Mostrar err toast msg
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Mostrar progreso de carga

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Cargando " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Por Favor Selecciona", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnShowListImage_Click(View v) {
        Intent i = new Intent(WS_Negocio.this, Image_List.class);
        startActivity(i);
    }
}