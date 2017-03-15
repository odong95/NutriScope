package edu.utdallas.csdesign.spring17.nutriscope.data;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Food;

/**
 * Created by john on 3/10/17.
 */

public class FoodClass {

    private String ndbNo;
    private String name;
    private int dataSource; // 1 sr 2 bp


    public FoodClass() {

    }

    public FoodClass(Food food) {
        this.ndbNo = food.getDesc().getNdbno();
        this.name = food.getDesc().getName();

        if (food.getDesc().getDs().toLowerCase().startsWith("s")) {
            this.dataSource = 1;
        } else {
            this.dataSource = 2;
        }
/*
        for(Nutrient nutrient: food.getNutrients()) {
            nutritionContent.add(new RealmNutrition(nutrient));
        }*/

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


}
