package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;

/**
 * Created by john on 2/21/17.
 */

public interface AddEditFoodContract {

    interface View extends BaseView<Presenter> {

        void showFood(String name, String protein, String fat, String carb);
        boolean isActive();



    }

    interface Presenter extends BasePresenter {

        void populateFood();
        void deleteFood();
        void addFood();

        boolean isDataMissing();




    }

}
