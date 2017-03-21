package edu.utdallas.csdesign.spring17.nutriscope;

import android.support.annotation.NonNull;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.FoodClass;

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
public class TestFirebase {
    String TAG = "TestFirebase";
    String uid;
    private CountDownLatch lock = new CountDownLatch(2);
    private String result;
    public TestFirebase() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Test
    public void test1() {


        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("test@test.com", "password").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                writeToDb();
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
        db.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User("john")).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Log.d(TAG, "complete");
                writeConsumedFood();
                lock.countDown();
            }
        });
    }


    void writeConsumedFood() {
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference();
        final ConsumedFood food = new ConsumedFood(new FoodClass(), "0324", "1", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        Log.d(TAG, getUid());


        String key = ref.child("foodconsumed").child(getUid()).push().getKey();

        int i = 0;
        Log.d(TAG, ++i + " " + key);


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/foodconsumed/" + getUid() + "/" + key, food.toMap());



        ref.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "COMPLETE");
                try {
                    task.getException();
                } catch (Exception ex) {
                    ex.printStackTrace();
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