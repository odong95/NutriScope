package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.threetenabp.AndroidThreeTen;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.utdallas.csdesign.spring17.nutriscope.login.LoginActivity;

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
