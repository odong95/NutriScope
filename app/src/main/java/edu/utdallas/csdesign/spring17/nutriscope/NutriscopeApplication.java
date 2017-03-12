<<<<<<< HEAD

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
=======
package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.threetenabp.AndroidThreeTen;

import dagger.Module;
>>>>>>> refs/remotes/jtexp/john-nightly

/**
 * Created by john on 3/5/17.
 */

<<<<<<< HEAD
public class NutriscopeApplication extends Application {

    private static NutriscopeApplication INSTANCE;
    public static final String AUTH_URL = "http://" + "n.uzsy.download" + ":9080/auth";
    public static final String REALM_URL = "realm://" + "n.uzsy.download" + ":9080/~/realmInstance";

    public static NutriscopeApplication getInstance() {
        return INSTANCE;

    }

=======
@Module
public class NutriscopeApplication extends Application {

    final private static String TAG = "NutriscopeApplication";

    FirebaseAuth auth;

    private Context getContext() {

        return getApplicationContext();
    }
>>>>>>> refs/remotes/jtexp/john-nightly

    @Override
    public void onCreate() {
        super.onCreate();

<<<<<<< HEAD
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
=======
        Injector.initialize(this);
        AndroidThreeTen.init(this);

        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("test@user.com", "password");



    }



    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }




>>>>>>> refs/remotes/jtexp/john-nightly
}
