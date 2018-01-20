package com.icloud.ganlensystems.pumitaschool;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Fragment_Contacto extends Fragment {
    public TextView Raul, David, Fernando, Efrain, Mariana, Bryan, Dulce, Carlos;


        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_contacto, container, false);
        return rootview;
    }
}