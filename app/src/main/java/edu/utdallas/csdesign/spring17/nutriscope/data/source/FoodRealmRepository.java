package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import android.util.Log;

import com.google.common.collect.Lists;

import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.FoodReport;
import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.FoodReportClient;
import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.FoodReportService;
import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.Food_;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.realm.RealmFood;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by john on 3/5/17.
 */

public class FoodRealmRepository implements Repository<RealmFood> {

    private final static String TAG = "FoodRealmRepository";

    private FoodReportService service = null;

    private static FoodRealmRepository INSTANCE = null;

    private FoodRealmRepository() {


    }

    private FoodRealmRepository(FoodReportService service) {
        this.service = service;
    }

    public static FoodRealmRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FoodRealmRepository(FoodReportClient.getInstance().getFoodReportService());
        }

        return INSTANCE;
    }

    @Override
    public void createItem(RealmFood item, CreateCallback callback) {





    }

    @Override
    public void updateItem(RealmFood item, UpdateCallback callback) {

    }

    @Override
    public void queryItem(Specification specification, final QueryCallback callback) {

        FoodRealmSpecification spec = (FoodRealmSpecification) specification;

        String id = spec.toRealmQuery();
        //check realm for id if doesn't exit


        // get new data
        Call<FoodReport> call = service.listReport(id);

        call.enqueue(new Callback<FoodReport>() {
            @Override
            public void onResponse(Call<FoodReport> call, Response<FoodReport> response) {
                FoodReport report = response.body();

                if (report != null) {
                    Food_ food = report.getFoods().get(0).getFood();

                    RealmFood realmFood = new RealmFood(food);
                    Log.d(TAG, food.getDesc().getName());

                    callback.onQueryComplete(Lists.newArrayList(realmFood));
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
    public void deleteItem(RealmFood id, DeleteCallback callback) {

    }


}
