package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;

/**
 * Created by john on 2/21/17.
 */

public interface AddEditFoodContract {

    interface View extends BaseView<Presenter> {

        void showOverview(String key);

        void populateContent(List<Object> content);

        boolean isActive();


    }

    interface Presenter extends BasePresenter {

        void populateFood();

        void deleteFood();

        void addFood(int quantity);

        boolean isDataMissing();


    }

}
