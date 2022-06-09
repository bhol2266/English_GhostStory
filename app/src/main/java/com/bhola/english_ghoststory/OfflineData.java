package com.bhola.english_ghoststory;

import android.app.Application;
import android.provider.Settings;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class OfflineData extends Application {

    FirebaseData ref;
    @Override
    public void onCreate() {
        super.onCreate();
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);



//    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
