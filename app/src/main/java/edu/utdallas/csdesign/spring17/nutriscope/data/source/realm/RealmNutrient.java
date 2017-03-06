package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by john on 3/5/17.
 */

public class RealmNutrient extends RealmObject {
    @PrimaryKey
    String name;


}
