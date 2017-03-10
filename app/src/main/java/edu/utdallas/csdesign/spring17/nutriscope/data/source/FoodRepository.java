package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import android.util.Log;

import com.google.common.collect.Lists;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.utdallas.csdesign.spring17.nutriscope.Injector;
import edu.utdallas.csdesign.spring17.nutriscope.data.FoodClass;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.Food;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.FoodReport;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.FoodReportService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by john on 3/10/17.
 */

@Module
public class FoodRepository implements Repository<Food> {

    private final static String TAG = "FoodRepository";


    FoodReportService foodReportService;

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

        FoodRealmSpecification spec = (FoodRealmSpecification) specification;

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

                    FoodClass foodClass = new FoodClass(food);
                    Log.d(TAG, food.getDesc().getName());


                    callback.onQueryComplete(Lists.newArrayList(foodClass));
                }

                else {
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
