package com.icloud.ganlensystems.pumitaschool;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;


public class MapaCU extends Fragment {


    public MapaCU() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mapa_cu, container, false);

        PhotoView photoView = (PhotoView) v.findViewById(R.id.photo_view);
        photoView.setImageResource(R.drawable.calendar);
        return v;

    }

}
