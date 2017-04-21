package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.ApplicationScope;
import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;

/**
 * Created by john on 4/17/17.
 */

@ApplicationScope
public class NutritionRepository implements Repository<Nutrition> {

    NutritionFirebaseRepository nutritionFirebaseRepository;

    public NutritionRepository(NutritionFirebaseRepository nutritionFirebaseRepository) {
        this.nutritionFirebaseRepository = nutritionFirebaseRepository;
    }



    public void createItem(Nutrition item, CreateCallback callback) {

    }

    public void updateItem(Nutrition item, UpdateCallback callback) {

    }

    public void queryItem(Specification specification, final QueryCallback<Nutrition> callback) {
        nutritionFirebaseRepository.queryItem(specification, new QueryCallback<Nutrition>() {
            @Override
            public void onQueryComplete(List<Nutrition> items) {
                callback.onQueryComplete(items);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void deleteItem(Nutrition id, DeleteCallback callback) {

    }

    public void getCalorieGoal(NutritionFirebaseRepository.CalorieCallback callback)
    {
         nutritionFirebaseRepository.getCalorieGoal(callback);
    }


}
