package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.collect.ImmutableList;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.FoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.FoodSpecification;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Food;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Nutrient;

/**
 * Created by john on 2/21/17.
 */

public class AddEditFoodPresenter implements AddEditFoodContract.Presenter {

    private static final String TAG = "AddEditFoodPresenter";
    @Inject @Nullable @Named("foodName") String foodName;
    @Inject @Nullable @Named("ndbId") String ndbId;
    private ConsumedFoodRepository consumedFoodRepository;
    private FoodRepository foodRepository;
    private AddEditFoodContract.View view;
    private Food food;
    private List<Object> contentList;

    @Inject
    public AddEditFoodPresenter(ConsumedFoodRepository consumedFoodRepository,
                                FoodRepository foodRepository,
                                AddEditFoodContract.View view) {
        this.consumedFoodRepository = consumedFoodRepository;
        this.foodRepository = foodRepository;
        this.view = view;
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
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
        if (getFood() != null) {


		ConsumedFood consumedFood = new ConsumedFood(getFood(), getFood().getDesc().getNdbno(), String.valueOf(quantity), LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
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


    }

    @Override
    public void populateFood() {

        if (ndbId != null) {
            Log.d(TAG, "ndbid not null");
            foodRepository.queryItem(new FoodSpecification(new ImmutableList.Builder<String>().add(ndbId).build()), new FoodRepository.QueryCallback<Food>() {
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
