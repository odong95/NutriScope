package edu.utdallas.csdesign.spring17.nutriscope.data.food;

import com.google.common.collect.ImmutableList;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;

/**
 * Created by john on 3/5/17.
 */

public class FoodSpecification implements Specification {

    private List<String> ids;

    public FoodSpecification(ImmutableList<String> ids) {
        this.ids = ids;
    }

    public List<String> getIds() {
        return ids;
    }
}

