package edu.utdallas.csdesign.spring17.nutriscope.util;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by john on 3/18/17.
 *
 * https://stackoverflow.com/questions/37673616/firebase-android-onauthstatechanged-called-twice
 *
 */

public class FirebaseLogger {

    final private static String TAG = "FirebaseLogger";

    String last_uid = null;
    String uid = null;


    public FirebaseLogger() {


        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, dataSnapshot.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.toString());


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
