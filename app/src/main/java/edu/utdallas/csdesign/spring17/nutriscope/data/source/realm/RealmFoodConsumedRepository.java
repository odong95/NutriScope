package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Specification;

/**
 * Created by john on 3/5/17.
 */

public class RealmFoodConsumedRepository implements Repository<RealmFoodConsumed> {

    private final static String TAG = "FoodRealmRepository";



    private static RealmFoodConsumedRepository INSTANCE = null;

    private RealmFoodConsumedRepository() {

    }

    public static RealmFoodConsumedRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new RealmFoodConsumedRepository();
        }

        return INSTANCE;
    }


    @Override
    public void createItem(RealmFoodConsumed item, CreateCallback callback) {

    }

    @Override
    public void updateItem(RealmFoodConsumed item, UpdateCallback callback) {

    }

    @Override
    public void queryItem(Specification specification, QueryCallback callback) {

    }

    @Override
    public void deleteItem(RealmFoodConsumed id, DeleteCallback callback) {

    }



}
