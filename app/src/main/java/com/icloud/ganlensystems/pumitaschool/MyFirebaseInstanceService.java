package com.icloud.ganlensystems.pumitaschool;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Raul on 19/12/17.
 */

public class MyFirebaseInstanceService extends FirebaseInstanceIdService {
    public static final String TAG = "Notificaciones";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: " + token);
    }
}
