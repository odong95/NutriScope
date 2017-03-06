package edu.utdallas.csdesign.spring17.nutriscope.data;

import io.realm.RealmObject;

/**
 * Created by john on 3/5/17.
 */

public class NutrientValue extends RealmObject{

    private Nutrient type;
    private int value;

}
