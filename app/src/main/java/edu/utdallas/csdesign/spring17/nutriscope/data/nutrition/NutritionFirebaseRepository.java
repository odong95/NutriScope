package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;

/**
 * Created by john on 4/17/17.
 */

final public class NutritionFirebaseRepository implements Repository<Nutrition> {


    private final static String TAG = "CFFirebaseRepo";

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;


    public NutritionFirebaseRepository() {

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }


    public void createItem(Nutrition item, CreateCallback callback) {

    }

    public void updateItem(Nutrition item, UpdateCallback callback) {

    }

    public void queryItem(Specification specification, QueryCallback<Nutrition> callback) {

    }

    public void deleteItem(Nutrition id, DeleteCallback callback) {

    }


}
