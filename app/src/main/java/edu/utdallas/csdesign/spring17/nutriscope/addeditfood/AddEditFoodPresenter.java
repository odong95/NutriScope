package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import android.util.Log;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.ConsumedFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.FoodClass;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ConsumedFoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.realm.FoodRealmSpecification;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.FoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.realm.RealmFoodConsumed;

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

    private FoodClass foodClass = null;
    private RealmFoodConsumed realmFoodConsumed = null;


    public FoodClass getFoodClass() {
        return foodClass;
    }

    public void setFoodClass(FoodClass foodClass) {
        this.foodClass = foodClass;
    }

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

        consumedFoodRepository.createItem(new ConsumedFood("01002", "lots"), new Repository.CreateCallback() {
            @Override
            public void onCreateComplete() {
                Log.d(TAG, "firebase success");
            }

            @Override
            public void onCreateFailed() {
                Log.d(TAG, "firebase fail");
            }
        });

    }

    @Override
    public void deleteFood() {

    }

    @Override
    public void addFood() {


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
                    setFoodClass((FoodClass) items.get(0));
                    view.showFoodName(getFoodClass().getName());
                //    view.makeNutrientsActive(getRealmfood().getNutritionContent());
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



}
