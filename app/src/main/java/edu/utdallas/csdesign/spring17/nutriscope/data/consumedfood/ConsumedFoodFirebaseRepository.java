package edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;


/**
 * Created by john on 3/10/17.
 */



public class ConsumedFoodFirebaseRepository implements Repository<ConsumedFood> {
    private final static String TAG = "CFFirebaseRepo";

    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth auth;



    public ConsumedFoodFirebaseRepository() {

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }


    @Override
    public void createItem(ConsumedFood item, CreateCallback callback) {

        Log.d(TAG, auth.getCurrentUser().getUid());
        Log.d(TAG, item.toString());

        String key = databaseReference.child("foodconsumed").child(auth.getCurrentUser().getUid()).push().getKey();

        Log.d(TAG, key);

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
    public void queryItem(Specification specification, final QueryCallback<ConsumedFood> callback) {
        Log.d(TAG, "quert initiated");
        final List<ConsumedFood> list = new LinkedList<>();

        FirebaseUser usr = auth.getCurrentUser();
        if (usr != null) {

            Query query = databaseReference.child("foodconsumed").child(auth.getCurrentUser().getUid());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot node : dataSnapshot.getChildren()) {
                        list.add(node.getValue(ConsumedFood.class));

                    }
                    Log.d(TAG, "query complete " + list.size() + " " + dataSnapshot.getChildrenCount());
                    callback.onQueryComplete(list);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onDataNotAvailable();

                }
            });
        }


    }

    @Override
    public void deleteItem(ConsumedFood id, DeleteCallback callback) {

    }

}
