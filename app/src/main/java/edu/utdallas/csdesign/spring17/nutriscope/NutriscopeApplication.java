package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.threetenabp.AndroidThreeTen;

import dagger.Module;

/**
 * Created by john on 3/5/17.
 */

@Module
public class NutriscopeApplication extends Application {

    final private static String TAG = "NutriscopeApplication";

    FirebaseAuth auth;

    private Context getContext() {

        return getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.initialize(this);
        AndroidThreeTen.init(this);

        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("test@user.com", "password");



    }



    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }




}
