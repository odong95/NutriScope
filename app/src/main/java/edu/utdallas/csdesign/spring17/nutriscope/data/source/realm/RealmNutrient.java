package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Nutrient;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by john on 3/5/17.
 */

public class RealmNutrient extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;

    public RealmNutrient() {


    }

    public RealmNutrient(Nutrient nutrient) {
        this.id = nutrient.getNutrientId();
        this.name = nutrient.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
