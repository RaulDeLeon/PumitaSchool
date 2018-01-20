package com.icloud.ganlensystems.pumitaschool;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.icloud.ganlensystems.pumitaschool.Objects.CustomInfoWindowAdapter;
import com.icloud.ganlensystems.pumitaschool.Objects.FirebaseReferences;
import com.icloud.ganlensystems.pumitaschool.Objects.Places;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "MapsActivity";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private static final int MY_LOCATION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(FirebaseReferences.PLACES_REFERENCE);

        if (verifyGooglePlayService()) {
            //Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.content_framemapa);
            mapFragment.getMapAsync(this);
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        createMap(googleMap);
        myRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot child : dataSnapshot.getChildren()) {
                    Places place = new Places();
                    place = child.getValue(Places.class);
                    googleMap.addCircle(createCircleMarkerMap(place.getLatitude(), place.getLongitude()));
                    googleMap.addMarker(createMarkerMap(place.getLatitude(), place.getLongitude(),
                            place.getName(), place.getSnippet()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Habilita GPS
        // if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return;
        //  }else{
        //mMap.setMyLocationEnabled(true);
        //  }

    }

    /**
     * Create a Map and configure it
     * @param mMap Map that is going to be configurate
     */
    public void createMap(GoogleMap mMap) {
        // Define variables
        float zoomLevel = 16;

        // Selected type of map and available the zoom settings

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        // Customize the styling of the map
        try {
            boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        // Add Event to infoWindow of the marker when its clicked
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intentMarkerInfo = new Intent(MapsActivity.this,
                        MarkerInfo.class);
                intentMarkerInfo.putExtra("INFO_PLACE", marker.getTitle());
                startActivity(intentMarkerInfo);
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.323297, -99.186030), zoomLevel));

        // Set a custom infoWindow
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
    }

    /**
     * Create a marker for a one place in the map.
     * @param latitude of the place
     * @param longitude of the place
     * @return the marker with its styles
     */
    public CircleOptions createCircleMarkerMap(double latitude, double longitude) {
        CircleOptions circleMarker = new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(10)
                .strokeWidth(15)
                .strokeColor(Color.YELLOW)
                .fillColor(Color.argb(255, 0, 0, 0));
        return circleMarker;
    }

    /**
     * Create a marker to the map width a custom icon.
     * @param latitude of the place
     * @param longitude of the place
     * @param titleMarker title of the place
     * @return the marker for the map
     */
    public MarkerOptions createMarkerMap(double latitude, double longitude,
                                         String titleMarker, String descriptionMarker) {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(titleMarker)
                .snippet(descriptionMarker)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

        return marker;
    }

    /**
     * Verify that the google play service is update and available.
     *
     */
    public boolean verifyGooglePlayService() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (status == ConnectionResult.SUCCESS) {
            return true;
        } else {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
        }

        return false;
    }


}
