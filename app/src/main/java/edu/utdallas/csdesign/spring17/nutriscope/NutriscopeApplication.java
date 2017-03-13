package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.lang.ref.WeakReference;

import dagger.Module;

/**
 * Created by john on 3/5/17.
 */

@Module
public class NutriscopeApplication extends Application {

    final private static String TAG = "NutriscopeApplication";

    FirebaseAuth auth;

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

        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("test@user.com", "password");


    }


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}
