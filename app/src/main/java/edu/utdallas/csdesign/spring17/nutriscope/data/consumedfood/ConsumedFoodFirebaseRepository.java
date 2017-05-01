package edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood;

import android.util.Log;

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

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.NullUser;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.User;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManager;


/**
 * Created by john on 3/10/17.
 */


final public class ConsumedFoodFirebaseRepository implements Repository<ConsumedFood> {
    private final static String TAG = "CFFirebaseRepo";

    private DatabaseReference databaseReference;

    private UserManager userManager;


    @Inject
    public ConsumedFoodFirebaseRepository(UserManager userManager) {
        this.userManager = userManager;
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }


    @Override
    public void createItem(final ConsumedFood item, final CreateCallback callback) {
        userManager.getUser(new UserManager.GetUser() {
            @Override
            public void getUser(User user) {
                String uid = user.getUid();

                Log.d(TAG, item.toString());

                String key = databaseReference.child("foodconsumed").child(uid).push().getKey();

                Log.d(TAG, key);

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/foodconsumed/" + uid + "/" + key, item.toMap());


                databaseReference.updateChildren(childUpdates);
                callback.onCreateComplete();

            }
        });

    }

    @Override
    public void updateItem(final ConsumedFood item, UpdateCallback callback) {
        userManager.getUser(new UserManager.GetUser() {
            @Override
            public void getUser(User user) {
                String uid = user.getUid();

                Query query = databaseReference.child("foodconsumed").equalTo(uid);
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

            }});
    }

    @Override
    public void queryItem(Specification specification, final QueryCallback<ConsumedFood> callback) {
        userManager.getUser(new UserManager.GetUser() {
            @Override
            public void getUser(User user) {
                String uid = user.getUid();

                Log.d(TAG, "query initiated");
                final List<ConsumedFood> list = new LinkedList<>();
                if (!(user instanceof NullUser)) {
                    Query query = databaseReference.child("foodconsumed").child(uid);

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
        });


    }

    @Override
    public void deleteItem(ConsumedFood id, DeleteCallback callback) {

    }

}
