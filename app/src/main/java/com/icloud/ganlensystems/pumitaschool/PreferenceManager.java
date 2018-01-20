package com.icloud.ganlensystems.pumitaschool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Raul on 16/11/17.
 */

public class PreferenceManager {

    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferenceManager(Context context)
    {
        this.context = context;
        getSharedPreferences();
    }

    private void getSharedPreferences()
    {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_prefer),Context.MODE_PRIVATE);
    }

    public void writePrefeneces()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_prefer_key),"INIT_OK");
        editor.commit();
    }

    public boolean checkPreference()
    {
        boolean status = false;
        if(sharedPreferences.getString(context.getString(R.string.my_prefer_key),"null").equals("null"))
        {
            status = false;
        }
        else {
            status = true;
        }
        return status;
    }

    public void clearPreference()
    {
        sharedPreferences.edit().clear().commit();
    }


}
