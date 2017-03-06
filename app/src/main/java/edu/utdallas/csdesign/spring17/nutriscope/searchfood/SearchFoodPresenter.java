package edu.utdallas.csdesign.spring17.nutriscope.searchfood;

import android.util.Log;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.ACResult;
import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.AutoSuggestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by john on 3/5/17.
 */

public class SearchFoodPresenter implements SearchFoodContract.Presenter {
    private final static String TAG = "SearchFoodPresenter";

    private AutoSuggestService service;

    SearchFoodContract.View searchFoodView;

    public SearchFoodPresenter(AutoSuggestService service, SearchFoodContract.View view) {
        this.service = service;
        this.searchFoodView = view;

    }


    public void start() {

    }

    @Override
    public void searchFood(String terms) {
        Call<List<ACResult>> call = service.autoComplete(terms);

        call.enqueue(new Callback<List<ACResult>>() {
            @Override
            public void onResponse(Call<List<ACResult>> call, Response<List<ACResult>> response) {
                //Log.d(TAG, response.toString());
                List<ACResult> out = response.body();

                Log.d(TAG, "onResponse");

                if (out != null) {
                    Log.d(TAG, "show results");

                    searchFoodView.showResults(out);
                }
            }

            @Override
            public void onFailure(Call<List<ACResult>> call, Throwable t) {
                Log.d(TAG, "failure" + t.getCause());
                t.printStackTrace();
                Log.d(TAG, t.toString());

            }
        });
    }

    @Override
    public void chooseFood(String ndbNo) {
        Log.d(TAG, ndbNo);
    }


    @Override
    public boolean isDataMissing() {

        return false;
    }

}
