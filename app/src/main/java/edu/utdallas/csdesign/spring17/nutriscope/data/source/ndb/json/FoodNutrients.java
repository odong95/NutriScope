package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json;

import edu.utdallas.csdesign.spring17.nutriscope.NutriscopeApplication;
import edu.utdallas.csdesign.spring17.nutriscope.R;

/**
 * Created by john on 2/23/17.
 */


public enum FoodNutrients {
    FAT(204, getString(R.string.fat), 65),
    PROTEIN(203, getString(R.string.protein), 50),
    CARBOHYDRATE(205, getString(R.string.carbohydrate),300),
    CALORIE(208, getString(R.string.kcalorie),0),
    NONE(0, "Does not exist.",0),
    FIBER(291, getString(R.string.fiber),25),
    SUGARS(269, getString(R.string.sugars), 30),
    CALCIUM(301, getString(R.string.calcium),1000),
    IRON(303, getString(R.string.iron), 18),
    MAGNESIUM(304, getString(R.string.magnesium), 400),
    PHOSPHORUS(305, getString(R.string.phosphorus), 1000),
    POTASSIUM(306, getString(R.string.potassium), 3500),
    SODIUM(307, getString(R.string.sodium), 2400),
    ZINC(309, getString(R.string.zinc), 15),
    SELENIUM(317, getString(R.string.selenium), 70),
    VITAMIN_A(318, getString(R.string.vitamin_a), 5000),
    VITAMIN_D(324, getString(R.string.vitamin_d), 400),
    VITAMIN_C(401, getString(R.string.vitamin_c), 60),
    THIAMIN(404, getString(R.string.thiamin), 1.5),
    RIBOFLAVIN(405, getString(R.string.riboflavin), 1.7),
    NIACIN(406, getString(R.string.niacin),20),
    VITAMIN_B6(415, getString(R.string.vitamin_b6), 2),
    VITAMIN_B12(418, getString(R.string.vitamin_b12), 2),
    VITAMIN_K(430, getString(R.string.vitamin_k), 80),
    FOLIC_ACID(431, getString(R.string.folic_acid), .4),
    CHOLESTEROL(601, getString(R.string.cholesterol),300);

    int nutrientId;

    String nutrientName;
    double nutrientValue;
    FoodNutrients(int id, String name, double val) {
        this.nutrientId = id;
        this.nutrientName = name;
        this.nutrientValue = val;
    }

    public static FoodNutrients getValue(int id) {
        FoodNutrients[] foodNutrients = FoodNutrients.values();
        for (int i = 0; i < foodNutrients.length; i++) {
            if (foodNutrients[i].equal(id)) {
                return foodNutrients[i];
            }
        }

        return FoodNutrients.NONE;
    }

    private static String getString(int id) {
        return NutriscopeApplication.getContext().getString(id);
    }

    public int getNutrientId() {
        return this.nutrientId;
    }

    public String getNutrientString() {
        return nutrientName;
    }

    public double getNutrientValue(){return nutrientValue; }

    public boolean equal(int i) {
        return nutrientId == i;
    }


}
