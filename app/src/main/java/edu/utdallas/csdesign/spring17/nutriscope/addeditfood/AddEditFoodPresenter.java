package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.Food;
import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.FoodReportService;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.FoodRealmSpecification;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Specification;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.realm.RealmFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.realm.RealmSpecification;

/**
 * Created by john on 2/21/17.
 */

public class AddEditFoodPresenter implements AddEditFoodContract.Presenter {

    Repository repository;
    AddEditFoodContract.View view;
    String foodName;
    String ndbId;


    public AddEditFoodPresenter(Repository repository, AddEditFoodContract.View view,
                                String ndbId, String foodName) {
        this.repository = repository;
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
    public void addFood() {

    }

    @Override
    public void populateFood() {
        if (foodName != null) {
            view.showFoodName(foodName);

        }

        if (ndbId != null) {
            repository.queryItem(new FoodRealmSpecification(ndbId), new Repository.QueryCallback() {
                @Override
                public void onQueryComplete(List items) {
                    RealmFood food = (RealmFood) items.get(0);
                    view.showFoodName(food.getName());
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
