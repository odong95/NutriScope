package edu.utdallas.csdesign.spring17.nutriscope.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 2/21/17.
 */

public class Food {
    private final String name;
    private final int servingSize;
    private final Map<Nutrients, Integer> nutrition;

    public Food(String name, int servingSize,int fat, int carbohydrates, int protein) {
        this.name = name;
        this.servingSize = servingSize;
        nutrition = new HashMap<>();
        nutrition.put(Nutrients.fat, fat);
        nutrition.put(Nutrients.carbohydrate, carbohydrates);
        nutrition.put(Nutrients.protein, protein);

    }


    public int getServingSize() {
        return servingSize;
    }

    public String getName() {
        return name;
    }

    public Map<Nutrients, Integer> getNutrition() {
        return nutrition;
    }


}
