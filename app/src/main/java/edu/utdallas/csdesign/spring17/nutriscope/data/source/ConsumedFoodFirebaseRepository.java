package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import edu.utdallas.csdesign.spring17.nutriscope.data.ConsumedFood;


/**
 * Created by john on 3/10/17.
 */

@Module
@Singleton
public class ConsumedFoodFirebaseRepository implements Repository<ConsumedFood> {
    private final static String TAG = "FoodRealmRepository";

    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth auth;


    @Inject
    public ConsumedFoodFirebaseRepository() {

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }


    @Override
    public void createItem(ConsumedFood item, CreateCallback callback) {

        Log.d(TAG, auth.getCurrentUser().getUid());

        String key = databaseReference.child("foodconsumed").child(auth.getCurrentUser().getUid()).push().getKey();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/foodconsumed/" + auth.getCurrentUser().getUid() + "/" + key, item.toMap());

        databaseReference.updateChildren(childUpdates);
        callback.onCreateComplete();

    }

    @Override
    public void updateItem(final ConsumedFood item, UpdateCallback callback) {
        Query query = databaseReference.child("foodconsumed").equalTo(auth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            ConsumedFood consumedFood;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodes = dataSnapshot.getChildren();
                for (DataSnapshot data : nodes) {
                    consumedFood = (ConsumedFood) data.getValue();

                    if (item.equals(consumedFood)) {
                        data.getRef().setValue(item);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void queryItem(Specification specification, QueryCallback callback) {


    }

    @Override
    public void deleteItem(ConsumedFood id, DeleteCallback callback) {

    }

}
