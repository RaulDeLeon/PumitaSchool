package com.icloud.ganlensystems.pumitaschool;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Raul on 29/12/17.
 */

public class ListViewAlumnos extends BaseAdapter {
        Activity activity;
        List<Registro_Alumno> lstAlumnos;
        LayoutInflater inflater;

    public ListViewAlumnos(Activity activity, List<Registro_Alumno> lstAlumnos) {
        this.activity = activity;
        this.lstAlumnos = lstAlumnos;
    }

    @Override
    public int getCount() {
        return lstAlumnos.size();
    }

    @Override
    public Object getItem(int i) {
        return lstAlumnos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
return null;
    }
}
