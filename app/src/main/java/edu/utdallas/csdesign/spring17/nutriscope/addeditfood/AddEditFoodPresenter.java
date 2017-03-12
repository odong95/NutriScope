package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.ConsumedFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ConsumedFoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.FoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.Food;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.realm.FoodRealmSpecification;

/**
 * Created by john on 2/21/17.
 */

public class AddEditFoodPresenter implements AddEditFoodContract.Presenter {

    private static final String TAG = "AddEditFoodPresenter";

    private ConsumedFoodRepository consumedFoodRepository;
    private FoodRepository foodRepository;
    private AddEditFoodContract.View view;
    private String foodName;
    private String ndbId;

    private Food food;


    public AddEditFoodPresenter(ConsumedFoodRepository consumedFoodRepository, FoodRepository foodRepository, AddEditFoodContract.View view,
                                String ndbId, String foodName) {
        this.consumedFoodRepository = consumedFoodRepository;
        this.foodRepository = foodRepository;
        this.view = view;
        this.ndbId = ndbId;
        this.foodName = foodName;
    }



    @Override
    public void start() {
        populateFood();

    }

    @Override
    public void deleteFood() {

    }

    @Override
    public void addFood(int quantity) {


        ConsumedFood consumedFood = new ConsumedFood(getFood().getDesc().getNdbno(), String.valueOf(quantity));
        consumedFood.setDateTimeConsumed(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond());

        consumedFoodRepository.createItem(consumedFood, new Repository.CreateCallback() {
            @Override
            public void onCreateComplete() {
                Log.d(TAG, "firebase success");
                view.showOverview("key");
            }

            @Override
            public void onCreateFailed() {
                Log.d(TAG, "firebase fail");
            }
        });






    }

    @Override
    public void populateFood() {
        if (foodName != null) {
            view.showFoodName(foodName);

        }

        if (ndbId != null) {
            Log.d(TAG, "ndbid null");
            foodRepository.queryItem(new FoodRealmSpecification(ndbId), new Repository.QueryCallback() {
                @Override
                public void onQueryComplete(List items) {
                    Food food = (Food) items.get(0);
                    setFood(food);

                    view.showFoodName(food.getDesc().getName());
                    view.makeNutrientsActive(((Food) items.get(0)).getNutrients());
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        }



    }

    @Override
    public boolean isDataMissing() {

        return false;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
