package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb;

import edu.utdallas.csdesign.spring17.nutriscope.NutriscopeApplication;
import edu.utdallas.csdesign.spring17.nutriscope.R;

/**
 * Created by john on 2/23/17.
 */


public enum FoodNutrients {
    FAT(204, getString(R.string.food_name)),
    PROTEIN(203, getString(R.string.protein)),
    CARBOHYDRATE(205, getString(R.string.carbohydrate)),
    CALORIE(208, getString(R.string.kcalorie)),
    NONE(0, "Does not exist."),
    FIBER(291, getString(R.string.fiber)),
    SUGARS(269, getString(R.string.sugars)),
    CALCIUM(301, getString(R.string.calcium)),
    IRON(303, getString(R.string.iron)),
    MAGNESIUM(304, getString(R.string.magnesium)),
    PHOSPHORUS(305, getString(R.string.phosphorus)),
    POTASSIUM(306, getString(R.string.potassium)),
    SODIUM(307, getString(R.string.sodium)),
    ZINC(309, getString(R.string.zinc)),
    SELENIUM(317, getString(R.string.selenium)),
    VITAMIN_A(318, getString(R.string.vitamin_a)),
    VITAMIN_D(324, getString(R.string.vitamin_d)),
    VITAMIN_C(401, getString(R.string.vitamin_c)),
    THIAMIN(404, getString(R.string.thiamin)),
    RIBOFLAVIN(405, getString(R.string.riboflavin)),
    NIACIN(406, getString(R.string.niacin)),
    VITAMIN_B6(415, getString(R.string.vitamin_b6)),
    VITAMIN_B12(418, getString(R.string.vitamin_b12)),
    VITAMIN_K(430, getString(R.string.vitamin_k)),
    FOLIC_ACID(431, getString(R.string.folic_acid)),
    CHOLESTEROL(601, getString(R.string.cholesterol));

    int nutrientId;

    String nutrientName;

    FoodNutrients(int id, String name) {
        this.nutrientId = id;
        this.nutrientName = name;
    }

    public int getNutrientId() {
        return this.nutrientId;
    }

    public String getNutrientString() {
        return nutrientName;
    }

    public boolean equal(int i) { return nutrientId == i; }

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




}
