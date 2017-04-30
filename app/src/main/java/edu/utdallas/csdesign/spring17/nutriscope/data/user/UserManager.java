package edu.utdallas.csdesign.spring17.nutriscope.data.user;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.CountDownLatch;

/**
 * Created by john on 4/29/17.
 */

public class UserManager {

    private static final String TAG = "UserManager";

    private User user;

    private String last_uid = null;
    private String uid = null;
    private CountDownLatch didAuthListenerFire = new CountDownLatch(1);

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");


    public UserManager() {
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Log.d(TAG, "User Logged Out " + uid);
                //    getLoginState().setUserStatus(FirebaseLogger.UserStatus.USER_LOGGED_OUT);
                    last_uid = uid;
                    uid = null;


                }
                else {
                    uid = firebaseAuth.getCurrentUser().getUid();
                    if (last_uid == null) {
                        Log.d(TAG, "User Logged In " + uid);
                 //       getLoginState().setUserStatus(FirebaseLogger.UserStatus.USER_LOGGED_IN);
                    } else {
                        if (uid.equals(last_uid)) {
                            Log.d(TAG, "User Token refresh" + uid);
                        }
                        else if (!uid.equals(last_uid) && last_uid != null) {
                            Log.d(TAG, "User Switched " + uid + " last: " + last_uid);
                 //           getLoginState().setUserStatus(FirebaseLogger.UserStatus.USER_SWITCHED);
                        }
                        else {
                            Log.d(TAG, "Extraneous auth state call");
                        }
                    }

                }

                didAuthListenerFire.countDown();
            }
        });
    }


    public void registerUserEmail(String email, String password, final TaskStatus taskStatus) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("AUTH", "createUserWithEmail:failed", task.getException());
                            taskStatus.failure(UserStatus.REGISTRATION_FAILED);
                        } else {
                            Log.d("AUTH", "createUserWithEmail:success:" + auth.getCurrentUser().getEmail());
                            taskStatus.success(UserStatus.REGISTRATION_SUCCESS);
                        }
                    }
                });

    }

    public void createUser() {
        try {
            this.user = new User(auth.getCurrentUser().getUid(), auth.getCurrentUser().getEmail());
        } catch (NullPointerException ex) {
            Log.w(TAG, "Could not get current user UID, could not create user");
        }

        db.child(user.getUid()).setValue(user);
    }

    public void loginUser() {


    }

    public void  logoutUser() {

    }

    public void modifyUser() {

    }

    public void getUserStatus(final TaskStatus taskStatus) {
        Log.d(TAG, "getUserStatus called");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    didAuthListenerFire.await();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    taskStatus.failure(UserStatus.USER_LOGGED_OUT);
                }


                if (uid != null) {
                    taskStatus.success(UserStatus.USER_LOGGED_IN);
                }
                else {
                    taskStatus.failure(UserStatus.USER_LOGGED_OUT);
                }

            }
        }).start();

    }








}
