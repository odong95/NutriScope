package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.support.annotation.NonNull;
import android.util.Log;

import org.threeten.bp.LocalDate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryItem;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.Nutrition;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.NutritionFirebaseSpecification;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.NutritionRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.FoodNutrients;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.User;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by john on 2/10/17.
 */

final class OverviewPresenter implements OverviewContract.Presenter {

    final private static String TAG = "OverviewPresenter";

    private final OverviewContract.View view;

    private final HistoryRepository historyRepository;

    private final NutritionRepository nutritionRepository;

    private final UserManager userManager;


    @Inject
    public OverviewPresenter(@NonNull OverviewContract.View view, HistoryRepository historyRepository, NutritionRepository nutritionRepository, UserManager userManager) {
        this.view = checkNotNull(view);
        this.historyRepository = historyRepository;
        this.nutritionRepository = nutritionRepository;
        this.userManager = userManager;

    }


    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }


    @Override
    public void start() {
        Log.d(TAG, "start");
        loadHistory();
        loadNutritionProgress();
    }

    @Override
    public void loadHistory() {

        historyRepository.queryItem(null, new HistoryRepository.QueryCallback<HistoryItem>() {
            @Override
            public void onQueryComplete(List<HistoryItem> items) {
                Log.d(TAG, "history list loaded " + items.size());
                for (HistoryItem item : items) {
                    if (item.getType() == ConsumedFood.class) {
                        Log.d(TAG, item.getItem(ConsumedFood.class).getKey());
                    }
                }
                view.showHistory(items);

            }

            @Override
            public void onDataNotAvailable() {


            }
        });


    }

    @Override
    public void openAddEditFood(ConsumedFood consumedFood) {
        view.showAddEditFood(consumedFood.getNdbNo());

    }

    @Override
    public void loadNutritionProgress() {

        userManager.getUser(new UserManager.GetUser() {
            @Override
            public void getUser(final User user) {

                nutritionRepository.queryItem(new NutritionFirebaseSpecification(LocalDate.now().toEpochDay(), LocalDate.now().toEpochDay()), new Repository.QueryCallback<Nutrition>() {
                    @Override
                    public void onQueryComplete(List<Nutrition> items) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        if (items.size() > 0) {
                            Iterator it = items.get(0).getNutrients().entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();

                                FoodNutrients fn = FoodNutrients.getValue(Integer.parseInt(pair.getKey().toString()));
                                Double val = (Double) pair.getValue();
                                double goal = fn.getNutrientValue();
                                String p = "";
                                if (fn.equal(208)) {
                                    double i = user.getCalorieGoal() - val;
                                    p = Double.toString(i);
                                } else {
                                    val /= goal;
                                    val *= 100;
                                    p = new DecimalFormat("##").format(val) + "%";
                                }

                                map.put(fn.getNutrientString(), p);
                                it.remove();
                            }
                        }
                        map.put("Calorie Goal", Integer.toString(user.getCalorieGoal()));
                        view.showNutritionProgress(map);
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            }


        });
    }

}
