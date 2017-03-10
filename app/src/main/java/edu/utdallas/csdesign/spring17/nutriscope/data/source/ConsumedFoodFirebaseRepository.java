package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.utdallas.csdesign.spring17.nutriscope.data.ConsumedFood;

/**
 * Created by john on 3/10/17.
 */

@Module
public class ConsumedFoodFirebaseRepository implements Repository<ConsumedFood> {
    private final static String TAG = "FoodRealmRepository";

    FirebaseDatabase firebaseDatabase;

    @Inject
    public ConsumedFoodFirebaseRepository(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;


    }




    @Override
    public void createItem(ConsumedFood item, CreateCallback callback) {

    }

    @Override
    public void updateItem(ConsumedFood item, UpdateCallback callback) {

    }

    @Override
    public void queryItem(Specification specification, QueryCallback callback) {

    }

    @Override
    public void deleteItem(ConsumedFood id, DeleteCallback callback) {

    }

}
