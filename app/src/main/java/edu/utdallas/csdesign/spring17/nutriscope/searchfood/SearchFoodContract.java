package edu.utdallas.csdesign.spring17.nutriscope.searchfood;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.Item;

/**
 * Created by john on 3/5/17.
 */

public class SearchFoodContract {

    interface View extends BaseView<SearchFoodContract.Presenter> {

        void showAddEditFood(String name, String id);

        void showResults(List<Item> results);

        boolean isActive();


    }

    interface Presenter extends BasePresenter {

        void searchFood(String terms);

        void chooseFood(String name, String id);

        boolean isDataMissing();


    }

}
