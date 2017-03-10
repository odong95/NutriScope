package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.threetenabp.AndroidThreeTen;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by john on 3/5/17.
 */

@Module
public class NutriscopeApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();

        Injector.initialize(this);
        AndroidThreeTen.init(this);



    }

    @Singleton
    @Provides
    FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance();

    }




}
