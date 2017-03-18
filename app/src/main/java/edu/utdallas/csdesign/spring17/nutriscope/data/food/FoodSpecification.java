package edu.utdallas.csdesign.spring17.nutriscope.data.food;

import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;

/**
 * Created by john on 3/5/17.
 */

public class FoodSpecification implements Specification {

    private String id;

    public FoodSpecification(String id) {
        this.id = id;
    }


    public String toRealmQuery() {
        return id;

    }


}

