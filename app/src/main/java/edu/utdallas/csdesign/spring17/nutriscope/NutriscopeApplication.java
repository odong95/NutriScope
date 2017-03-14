
package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.jakewharton.threetenabp.AndroidThreeTen;


public class NutriscopeApplication extends Application {

    private static NutriscopeApplication INSTANCE;


    public static NutriscopeApplication getInstance() {
        return INSTANCE;

    }


    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
