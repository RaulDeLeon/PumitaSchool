package com.icloud.ganlensystems.pumitaschool.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.icloud.ganlensystems.pumitaschool.R;


/**
 * Created by drewermerc on 23/11/17.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_infowindow, null);
    }

    private void renderWindowText(Marker marker, View view){
        String title = marker.getTitle();
        String snippet = marker.getSnippet();

        TextView titleTextView = (TextView) view.findViewById(R.id.title_place_text_view);
        if(!title.equals("")){
            titleTextView.setText(title);
        }

        TextView snippetTextView = (TextView) view.findViewById(R.id.description_place_text_view);
        if(!title.equals("")){
            snippetTextView.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}
