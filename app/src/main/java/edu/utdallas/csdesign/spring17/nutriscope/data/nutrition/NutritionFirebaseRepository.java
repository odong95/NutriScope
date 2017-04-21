package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import android.text.TextUtils;

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
import edu.utdallas.csdesign.spring17.nutriscope.data.source.firebase.User;

/**
 * Created by john on 4/17/17.
 */

final public class NutritionFirebaseRepository implements Repository<Nutrition> {


    private final static String TAG = "CFFirebaseRepo";

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private int calGoal = 0;


    public NutritionFirebaseRepository() {

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }


    public void createItem(Nutrition item, CreateCallback callback) {

    }

    public void updateItem(Nutrition item, UpdateCallback callback) {

    }

    public void queryItem(Specification specification, final QueryCallback<Nutrition> callback) {
        final List<Nutrition> list = new LinkedList<>();

        FirebaseUser usr = auth.getCurrentUser();
        long start = ((NutritionFirebaseSpecification)specification).getStartDay();
        long end = ((NutritionFirebaseSpecification)specification).getEndDay();
        if (usr != null) {

            Query query = databaseReference.child("nutrition").child(usr.getUid()).orderByChild("dateStamp").startAt(start).endAt(end);

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

    public void deleteItem(Nutrition id, DeleteCallback callback) {

    }

    public void getCalorieGoal(final CalorieCallback callback)
    {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                if(!TextUtils.isEmpty(u.getCalorieGoal())){
                    calGoal = Integer.parseInt(u.getCalorieGoal());
                    callback.onChanged(calGoal);
                }
                else
                {
                    callback.onChanged(0);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        db.addListenerForSingleValueEvent(valueEventListener);

    }



    public interface CalorieCallback{
        void onChanged(int calGoal);
    }


}
