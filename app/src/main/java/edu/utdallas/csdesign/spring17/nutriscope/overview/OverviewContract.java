package edu.utdallas.csdesign.spring17.nutriscope.overview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryItem;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.Nutrition;

/**
 * Created by john on 2/10/17.
 */

public interface OverviewContract {

    interface View extends BaseView<Presenter> {

        void showHistory(List<HistoryItem> data);

        void showAddEditFood(String ndbNo);

        void showNutritionProgress(HashMap<String,String> data);

    }

    interface Presenter extends BasePresenter {

        void loadHistory();

        void openAddEditFood(ConsumedFood consumedFood);

        void loadNutritionProgress();

    }

}
