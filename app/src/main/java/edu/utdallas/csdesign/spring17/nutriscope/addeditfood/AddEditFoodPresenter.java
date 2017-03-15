package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.util.LinkedList;
import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.ConsumedFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ConsumedFoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.FoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Food;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Nutrient;
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

    private List<Object> contentList;

    @Override
    public void populateFood() {
/*        if (foodName != null) {
            view.showFoodName(foodName);

        }*/

        if (ndbId != null) {
            Log.d(TAG, "ndbid not null");
            foodRepository.queryItem(new FoodRealmSpecification(ndbId), new Repository.QueryCallback() {
                @Override
                public void onQueryComplete(List items) {
                    Food food = (Food) items.get(0);
                    setFood(food);


                    contentList = new LinkedList<Object>();
                    contentList.add(new FoodName(food.getDesc().getName()));
                    contentList.add(new Quantity(""));
                    for (Nutrient nutrient : ((Food) items.get(0)).getNutrients()) {
                        contentList.add(nutrient);

                    }

                    view.populateContent(contentList);

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
