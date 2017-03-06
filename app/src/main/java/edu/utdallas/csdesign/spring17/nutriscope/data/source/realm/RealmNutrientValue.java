package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import io.realm.RealmObject;

/**
 * Created by john on 3/5/17.
 */

public class RealmNutrientValue extends RealmObject{

    private RealmNutrient type;
    private int value;

}
