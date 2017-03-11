package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.realm.RealmSpecification;

/**
 * Created by john on 3/5/17.
 */

public class FoodRealmSpecification implements RealmSpecification {

    private String id;

    public FoodRealmSpecification(String id) {
        this.id = id;
    }

    @Override
    public String toRealmQuery() {
        return id;

    }



}

