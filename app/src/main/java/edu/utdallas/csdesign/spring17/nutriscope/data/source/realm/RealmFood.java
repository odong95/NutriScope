package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by john on 2/21/17.
 */

public class RealmFood extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;
    private RealmList<RealmNutrientValue> nutritionContent;

    public RealmFood() {

    }

    public RealmFood(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public RealmFood(String name) {
        this.name = name;


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
