package edu.utdallas.csdesign.spring17.nutriscope;

import android.support.annotation.NonNull;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFood;

/**
 * {
 * "rules": {
 * <p>
 * "foodconsumed": {
 * "$userid": {
 * "$id": {
 * ".validate": "newData.hasChildren(['ndbNo', 'quantity', 'dateTimeConsumed'])",
 * "ndbNo": {
 * ".validate": "newData.isString()"
 * },
 * "quantity": {
 * ".validate": "newData.isString()"
 * },
 * "$other": {
 * },
 * },
 * ".read": "auth != null && auth.uid == $userid",
 * ".write": "auth != null && auth.uid == $userid",
 * },
 * <p>
 * <p>
 * <p>
 * },
 * "users": {
 * "$userid": {
 * ".validate": "newData.hasChildren(['name'])",
 * "name": {
 * ".validate": "newData.isString()"
 * },
 * "$other": {
 * ".validate": "false"
 * },
 * ".read": "true",
 * ".write": "auth != null && auth.uid == $userid"
 * }
 * },
 * <p>
 * }
 * }
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestFirebase2 {
    String TAG = "TestFirebase";
    String uid;
    private CountDownLatch lock = new CountDownLatch(1);
    private String result;
    public TestFirebase2() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Test
    public void test2() {


        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("test@test.com", "password").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    writeToDb();
                }
                else {
                    task.getException();
                }
            }
        });


        try {
            lock.await(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();

        }

    }

    void writeToDb() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        db.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, " users snap " + dataSnapshot.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "users error " + databaseError.toString() + databaseError.getDetails() + databaseError.getMessage());


            }
        });

        db.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User("john")).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "complete");
                    writeConsumedFood();
                }
                else {
                        task.getException();
                    lock.countDown();
                    }

            }
        });
    }


    void writeConsumedFood() {
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference();
        final ConsumedFood food = new ConsumedFood("0324", "1", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        Log.d(TAG, getUid());

        db.getReference().child("foodconsumed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "foodcons. snap " + dataSnapshot.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "foodcons. error " + databaseError.toString() + databaseError.getDetails() + databaseError.getMessage());


            }
        });

        String key = ref.child("foodconsumed").child(getUid()).push().getKey();

        int i = 0;
        Log.d(TAG, ++i + " key " + key);


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/foodconsumed/" + getUid() + "/" + key, food.toMap());



        ref.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "COMPLETE");
                }
                else {
                    task.getException();
                }
                lock.countDown();

            }
        });


    }


    @IgnoreExtraProperties
    public class User {
        public String name = "John";

        public User() {

        }

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}