package edu.utdallas.csdesign.spring17.nutriscope.Util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by john on 3/18/17.
 */

public class FirebaseLogger {

    final private static String TAG = "FirebaseLogger";

    String last_uid;
    String uid;


    public FirebaseLogger() {



        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                Log.d(TAG, dataSnapshot.toString());

            }

            @Override
            public void onCancelled (DatabaseError databaseError){
                Log.d(TAG, databaseError.toString());


        }
    });



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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

    private FirebaseLoginState loginState = new FirebaseLoginState();

    public FirebaseLoginState getLoginState() {
        return loginState;
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
