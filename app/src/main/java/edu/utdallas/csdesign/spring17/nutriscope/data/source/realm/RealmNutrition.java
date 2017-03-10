package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.Nutrient;
import io.realm.RealmObject;

/**
 * Created by john on 3/5/17.
 */

public class RealmNutrition extends RealmObject{
    private RealmNutrient nutrient;
    private Double amount;
    private int unit; // 0 unknown 1 g 2 mg 3 ml 4 kcal

    public RealmNutrition() {

    }

    public RealmNutrition(Nutrient nutrient) {

        this.nutrient = new RealmNutrient(nutrient);
        this.amount = Double.parseDouble(nutrient.getValue());
        if (nutrient.getUnit().toLowerCase().startsWith("g")) {
            this.unit = 1;
        }
        else if (nutrient.getUnit().toLowerCase().startsWith("mg")) {
            this.unit = 2;
        }
        else if (nutrient.getUnit().toLowerCase().startsWith("ml")) {
            this.unit = 3;
        }
        else if (nutrient.getUnit().toLowerCase().startsWith("kcal")) {
            this.unit = 4;
        }
        else {
            this.unit = 0;
        }

    }

    public RealmNutrient getNutrient() {
        return nutrient;
    }

    public Double getAmount() {
        return amount;
    }

    public int getUnit() {
        return unit;
    }
}
