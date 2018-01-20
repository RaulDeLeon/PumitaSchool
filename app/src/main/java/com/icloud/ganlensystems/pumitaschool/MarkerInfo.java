package com.icloud.ganlensystems.pumitaschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.icloud.ganlensystems.pumitaschool.Objects.DownloadImageTask;
import com.icloud.ganlensystems.pumitaschool.Objects.FirebaseReferences;
import com.icloud.ganlensystems.pumitaschool.Objects.Places;


public class MarkerInfo extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_info);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(FirebaseReferences.PLACES_REFERENCE);

        String namePlace = getIntent().getStringExtra("INFO_PLACE");
        fillinActivity(namePlace);
    }

    public void fillinActivity(final String place){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Places placeInfo = new Places();
                    placeInfo = child.getValue(Places.class);
                    String name = placeInfo.getName();

                    if(name.equals(place)){
                        if(!placeInfo.getName().equals("")) {
                            TextView titleInfo = (TextView) findViewById(R.id.title_info_text_view);
                            titleInfo.setText(placeInfo.getName());
                        }
                        if(!placeInfo.getDescription().equals("")) {
                            TextView descriptionInfo = (TextView) findViewById(R.id.description_info_text_view);
                            descriptionInfo.setText(placeInfo.getDescription());
                        }
                        if(!placeInfo.getAddress().equals("")) {
                            TextView location = (TextView) findViewById(R.id.location_text_view);
                            location.setText(placeInfo.getAddress());
                        }
                        if(!placeInfo.getSchedule().equals("")) {
                            TextView schedule = (TextView) findViewById(R.id.schedule_text_view);
                            schedule.setText(placeInfo.getSchedule());
                        }
                        if(!placeInfo.getPhone().equals("")) {
                            TextView phone = (TextView) findViewById(R.id.phone_text_view);
                            phone.setText(placeInfo.getPhone());
                        }
                        if(!placeInfo.getLink().equals("")) {
                            TextView link = (TextView) findViewById(R.id.link_text_view);
                            link.setText(placeInfo.getLink());
                        }
                        if(!placeInfo.getImage().equals("")) {
                            ImageView imagePlace = (ImageView) findViewById(R.id.image_place_text_view);
                            new DownloadImageTask(imagePlace).execute(placeInfo.getImage());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
