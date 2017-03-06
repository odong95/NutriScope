package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import edu.utdallas.csdesign.spring17.nutriscope.data.FoodConsumed;
import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.FoodReport;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.realm.RealmRepository;

/**
 * Created by john on 3/5/17.
 */

public class NutritionDataService {

    private RealmRepository repo = RealmRepository.getInstance();

    FoodReport report;


    public void searchFood(String terms) {



    }






    public void saveFoodConsumed(FoodConsumed foodConsumed) {



        repo.createTrackable(foodConsumed, new Repository.CreateCallback() {
            @Override
            public void onCreateComplete() {

            }

            @Override
            public void onCreateFailed() {

            }
        });


    }




}
