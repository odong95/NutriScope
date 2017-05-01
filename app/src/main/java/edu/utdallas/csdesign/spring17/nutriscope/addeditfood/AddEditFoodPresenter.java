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
    @Inject Boolean isConsumedFood;
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
        lookupFood();

    }

    @Override
    public void deleteFood() {

    }

    @Override
    public void addFood(int quantity) {
        if (getFood() != null) {

		ConsumedFood consumedFood = new ConsumedFood(getFood(), getFood().getDesc().getNdbno(), getQuantity(), LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
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


    private void populateFood(Food food, double quantity) {
        setFood(food);
        contentList = new LinkedList<>();
        contentList.add(new FoodName(food.getDesc().getName()));
        contentList.add(new Quantity(quantity, food.getNutrients().get(0).getMeasures()));
        for (Nutrient nutrient : food.getNutrients()) {
            contentList.add(nutrient);

        }

        view.populateContent(contentList);
    }


    private void lookupFood() {
        if (ndbId != null) {
            Log.d(TAG, "ndbid not null");

            if (isConsumedFood) {
                Log.d(TAG, "consumed food");
                consumedFoodRepository.queryItem(null, new ConsumedFoodRepository.QueryCallback<ConsumedFood>() {

                    @Override
                    public void onQueryComplete(List<ConsumedFood> items) {
                        for(ConsumedFood item: items) {
                            if(item.getNdbNo().equals(ndbId)) {
                                populateFood(item.getFood(), item.getQuantity());
                            }
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            }
            else {
                foodRepository.queryItem(new FoodSpecification(new ImmutableList.Builder<String>().add(ndbId).build()), new FoodRepository.QueryCallback<Food>() {
                    @Override
                    public void onQueryComplete(List items) {
                        Food food = (Food) items.get(0);
                        populateFood(food, 1.0);

                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            }
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

    /**
     * This will calculate the quantity in grames based on the qty. and measure user selected.
     * @return
     */
    private double getQuantity() {
        for (Object obj: contentList) {
            if (obj instanceof Quantity) {
                Quantity q = ((Quantity) obj);
                if (q.getMeasures().get(q.getSelectedMeasure()).getEunit().equals("g"))
                    return q.getQuantity() * q.getMeasures().get(q.getSelectedMeasure()).getEqv();
                else
                    throw new RuntimeException("Unknown Eunit");
            }
        }
        return 0;
    }

}
