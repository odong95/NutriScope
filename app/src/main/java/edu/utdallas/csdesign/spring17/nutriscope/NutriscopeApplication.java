
package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.stetho.Stetho;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.log.LogLevel;
import io.realm.log.RealmLog;

/**
 * Created by john on 3/5/17.
 */

public class NutriscopeApplication extends Application {

    private static NutriscopeApplication INSTANCE;
    public static final String AUTH_URL = "http://" + "n.uzsy.download" + ":9080/auth";
    public static final String REALM_URL = "realm://" + "n.uzsy.download" + ":9080/~/realmInstance";

    public static NutriscopeApplication getInstance() {
        return INSTANCE;

    }


    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);
        Realm.init(this);
        FacebookSdk.sdkInitialize(this);
        RealmLog.setLevel(LogLevel.TRACE);

        /*
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());*/

    }
}
