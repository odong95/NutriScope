package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.Nutrient;

/**
 * Created by john on 2/21/17.
 */

public interface AddEditFoodContract {

    interface View extends BaseView<Presenter> {

        void makeNutrientsActive(List<Nutrient> nutrients);
        void showOverview(String key);
        void showFoodName(String name);
        void showFood(String name, String protein, String fat, String carb);
        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void populateFood();
        void deleteFood();
        void addFood(int quantity);

        boolean isDataMissing();




    }

}
