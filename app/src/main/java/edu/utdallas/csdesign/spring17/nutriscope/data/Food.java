package edu.utdallas.csdesign.spring17.nutriscope.data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by john on 2/21/17.
 */

public class Food extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;
    private RealmList<NutrientValue> nutritionContent;

    public Food() {

    }

    public Food(String name) {
        this.name = name;


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
