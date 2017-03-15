package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.jakewharton.threetenabp.AndroidThreeTen;


public class NutriscopeApplication extends Application {

    final private static String TAG = "NutriscopeApplication";


    private static WeakReference<Context> context;

    public static Context getContext() {

        return context.get();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<Context>(this);
        Injector.initialize(this);
        AndroidThreeTen.init(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }



    }
}
