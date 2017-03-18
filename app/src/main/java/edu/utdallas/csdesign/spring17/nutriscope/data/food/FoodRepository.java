package edu.utdallas.csdesign.spring17.nutriscope.data.food;

import android.util.Log;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.FoodReportService;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Food;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.FoodReport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by john on 3/10/17.
 */


public class FoodRepository implements Repository<Food> {

    private final static String TAG = "FoodRepository";


    FoodReportService foodReportService;
    Map<String, Food> foodCache = new HashMap<>();

    public FoodRepository() {

    }

    @Inject
    public FoodRepository(FoodReportService foodReportService) {
        this.foodReportService = foodReportService;


    }

    @Override
    public void createItem(Food item, CreateCallback callback) {

    }

    @Override
    public void updateItem(Food item, UpdateCallback callback) {

    }

    @Override
    public void queryItem(Specification specification, final QueryCallback callback) {

        FoodSpecification spec = (FoodSpecification) specification;

        String id = spec.toRealmQuery();
        //check realm for id if doesn't exit

        // get new data
        Call<FoodReport> call = foodReportService.listReport(id);

        call.enqueue(new Callback<FoodReport>() {
            @Override
            public void onResponse(Call<FoodReport> call, Response<FoodReport> response) {
                FoodReport report = response.body();

                if (report != null) {
                    Food food = report.getFoods().get(0).getFood();

                    Log.d(TAG, food.getDesc().getName());

                    callback.onQueryComplete(Lists.newArrayList(food));
                } else {
                    callback.onDataNotAvailable();
                }

            }

            @Override
            public void onFailure(Call<FoodReport> call, Throwable t) {
                Log.d(TAG, "food report failure");
                callback.onDataNotAvailable();

            }
        });


    }

    @Override
    public void deleteItem(Food id, DeleteCallback callback) {

    }


}
