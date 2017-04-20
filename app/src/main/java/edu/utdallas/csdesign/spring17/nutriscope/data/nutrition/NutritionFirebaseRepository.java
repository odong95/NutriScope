package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.threeten.bp.LocalDate;

import java.util.LinkedList;
import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;

/**
 * Keeps track of the Nutrition history for a user in Firebase db.
 */

final public class NutritionFirebaseRepository implements Repository<Nutrition> {


    private final static String TAG = "CFFirebaseRepo";

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;


    public NutritionFirebaseRepository() {

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    /**
     *  Not a necessary operation
     */
    public void createItem(Nutrition item, CreateCallback callback) {
        throw new UnsupportedOperationException();
    }

    /**
     * This class should update the Nutrition info for 1 day.
     *
     * @param item
     * @param callback
     */

    public void updateItem(Nutrition item, UpdateCallback callback) {


    }

    /**
     * This class should query the nutrition info.
     *
     * The specification should specify the number of days to return.
     *
     * @param specification
     * @param callback
     */

    public void queryItem(Specification specification, final QueryCallback<Nutrition> callback) {
        final List<Nutrition> list = new LinkedList<>();

        FirebaseUser usr = auth.getCurrentUser();

        if (usr != null) {

            Query query = databaseReference.child("nutrition").child(usr.getUid());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot node : dataSnapshot.getChildren()) {

                       list.add(node.getValue(Nutrition.class));

                    }

                    callback.onQueryComplete(list);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onDataNotAvailable();

                }
            });
        }
    }

    /**
     * Will not be implemented     *
     */
    public void deleteItem(Nutrition id, DeleteCallback callback) {
        throw new UnsupportedOperationException();
    }


}
