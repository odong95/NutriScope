package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.lang.ref.WeakReference;
import java.util.Observable;
import java.util.Observer;

import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFoodRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFoodRepositoryModule;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.DaggerConsumedFoodRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.DaggerFoodRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.FoodRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.FoodRepositoryModule;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.DaggerHistoryRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepositoryModule;


public class NutriscopeApplication extends Application {

    final private static String TAG = "NutriscopeApplication";
    private static WeakReference<Context> context;
    FirebaseAuth firebaseAuth;
    String last_uid;
    String uid;
    FirebaseLoginState loginState = new FirebaseLoginState();
    private HistoryRepositoryComponent historyRepositoryComponent;
    private ConsumedFoodRepositoryComponent consumedFoodRepositoryComponent;
    private FoodRepositoryComponent foodRepositoryComponent;
    private NetComponent netComponent;

    public static Context getContext() {

        return context.get();
    }

    public FirebaseLoginState getLoginState() {
        return loginState;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<Context>(this);
        AndroidThreeTen.init(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        netComponent = DaggerNetComponent.builder().build();

        historyRepositoryComponent = DaggerHistoryRepositoryComponent.builder().historyRepositoryModule(new HistoryRepositoryModule(new HistoryRepository())).build();

        consumedFoodRepositoryComponent = DaggerConsumedFoodRepositoryComponent.builder().consumedFoodRepositoryModule(new ConsumedFoodRepositoryModule()).build();

        foodRepositoryComponent = DaggerFoodRepositoryComponent.builder().foodRepositoryModule(new FoodRepositoryModule()).build();


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // user signed out
                if (firebaseAuth.getCurrentUser() == null) {
                    getLoginState().setUserStatus(UserStatus.USER_LOGGED_OUT);
                    Log.d(TAG, "User Logged Out " + uid);
                    last_uid = uid;
                    uid = null;


                }
                // user signed in
                else {

                    if (uid == null && last_uid == null) {

                        uid = firebaseAuth.getCurrentUser().getUid();
                        getLoginState().setUserStatus(UserStatus.USER_LOGGED_IN);
                        Log.d(TAG, "User Logged In " + uid);
                    } else {

                        uid = firebaseAuth.getCurrentUser().getUid();
                        // token refresh
                        if (last_uid == uid) {

                            Log.d(TAG, "User Token refresh" + uid);

                        }
                        // user switched
                        else {
                            getLoginState().setUserStatus(UserStatus.USER_SWITCHED);
                            Log.d(TAG, "User Switched " + uid + " last: " + last_uid);
                        }
                    }

                }


            }
        });


    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public HistoryRepositoryComponent getHistoryRepositoryComponent() {
        return historyRepositoryComponent;
    }

    public ConsumedFoodRepositoryComponent getConsumedFoodRepositoryComponent() {
        return consumedFoodRepositoryComponent;
    }

    public FoodRepositoryComponent getFoodRepositoryComponent() {
        return foodRepositoryComponent;
    }

    enum UserStatus {
        USER_LOGGED_IN,
        USER_LOGGED_OUT,
        USER_SWITCHED,
    }

    class FirebaseLoginState extends Observable {
        UserStatus userStatus;

        public UserStatus getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
            setChanged();
            notifyObservers(getUserStatus());

        }
    }

    class FirebaseLoginListener implements Observer {
        @Override
        public void update(Observable observable, Object arg) {
            Log.d(TAG, "Login listener update called");


        }
    }


}

