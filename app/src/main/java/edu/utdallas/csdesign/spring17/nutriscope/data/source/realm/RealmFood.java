package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Food;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Nutrient;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by john on 2/21/17.
 */

public class RealmFood extends RealmObject {

    @PrimaryKey
    private String ndbNo;
    private String name;
    private int dataSource; // 1 sr 2 bp
    private RealmList<RealmNutrition> nutritionContent = new RealmList<>();


    public RealmFood() {

    }

    public RealmFood(Food food) {
        this.ndbNo = food.getDesc().getNdbno();
        this.name = food.getDesc().getName();

        if (food.getDesc().getDs().toLowerCase().startsWith("s")) {
            this.dataSource = 1;
        } else {
            this.dataSource = 2;
        }

        for (Nutrient nutrient : food.getNutrients()) {
            nutritionContent.add(new RealmNutrition(nutrient));
        }

    }

    public String getNdbNo() {
        return ndbNo;
    }

    public String getName() {
        return name;
    }

    public int getDataSource() {
        return dataSource;
    }

    public RealmList<RealmNutrition> getNutritionContent() {
        return nutritionContent;
    }
}
