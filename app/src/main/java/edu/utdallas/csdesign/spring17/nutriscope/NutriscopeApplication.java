package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import io.realm.Realm;
import okhttp3.OkHttpClient;

/**
 * Created by john on 3/5/17.
 */

public class NutriscopeApplication extends Application {

    private static NutriscopeApplication INSTANCE;

    public static NutriscopeApplication getInstance() {
        return INSTANCE;

    }


    private OkHttpClient client = new OkHttpClient();


    public OkHttpClient getClient() {
        return client;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);
        Realm.init(this);



    }




}
