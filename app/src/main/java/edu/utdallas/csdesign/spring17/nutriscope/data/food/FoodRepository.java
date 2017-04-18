package edu.utdallas.csdesign.spring17.nutriscope.data.food;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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


final public class FoodRepository implements Repository<Food> {

    private final static String TAG = "FoodRepository";


    private FoodReportService foodReportService;

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
    public void queryItem(Specification specification, final QueryCallback<Food> callback) {
        final List<Food> output = new ArrayList<>();

        final FoodSpecification spec = (FoodSpecification) specification;

        for (int i = 0; i < spec.getIds().size(); i++) {
            final int index = i;

            // get new data
            Call<FoodReport> call = foodReportService.listReport(spec.getIds().get(i));

            call.enqueue(new Callback<FoodReport>() {
                @Override
                public void onResponse(Call<FoodReport> call, Response<FoodReport> response) {
                    if(response.isSuccessful()) {
                        FoodReport report = response.body();

                        if (report != null) {
                            Food food = report.getFoods().get(0).getFood();

                            Log.d(TAG, food.getDesc().getName());
                            output.add(food);
                            if (index == spec.getIds().size() - 1) {
                                Log.d(TAG, "send up " + output.size());
                                callback.onQueryComplete(output);
                            }
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                }

                @Override
                public void onFailure(Call<FoodReport> call, Throwable t) {
                    Log.d(TAG, "food report failure");
                    callback.onDataNotAvailable();

                }
            });

        }

    }

    @Override
    public void deleteItem(Food id, DeleteCallback callback) {

    }


}
