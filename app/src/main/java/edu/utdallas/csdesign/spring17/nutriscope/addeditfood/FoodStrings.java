package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.FoodNutrients;

/**
 * Created by john on 2/23/17.
 */

public class FoodStrings {

    public static int returnString(FoodNutrients nutrient) {

        switch (nutrient) {
            case FAT:
                return R.string.fat;
            case CARBOHYDRATE:
                return R.string.carbohydrate;
            case PROTEIN:
                return R.string.protein;
            case CALORIE:
                return R.string.kcalorie;
            default:
                return -1;
        }
    }

}
