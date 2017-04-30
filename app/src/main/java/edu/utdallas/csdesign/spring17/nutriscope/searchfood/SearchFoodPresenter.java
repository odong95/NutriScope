package edu.utdallas.csdesign.spring17.nutriscope.searchfood;

import android.util.Log;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.AutoSuggestService;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.Search;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by john on 3/5/17.
 */

public class SearchFoodPresenter implements SearchFoodContract.Presenter {
    private final static String TAG = "SearchFoodPresenter";
    SearchFoodContract.View searchFoodView;
    private AutoSuggestService service;

    @Inject
    public SearchFoodPresenter(AutoSuggestService service, SearchFoodContract.View view) {
        this.service = service;
        this.searchFoodView = view;

    }

    @Inject
    void setupListeners() {
        searchFoodView.setPresenter(this);
    }


    public void start() {

    }

    @Override
    public void searchFood(String terms) {

        Call<Search> call = service.autoComplete(terms);

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                //Log.d(TAG, response.toString());
                Search out = response.body();

                Log.d(TAG, "onResponse");

                if (out.getList() != null) {
                    Log.d(TAG, "show results");
                    searchFoodView.showResults(out.getList().getItem());
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.d(TAG, "failure" + t.getCause());
                t.printStackTrace();
                Log.d(TAG, t.toString());

            }
        });
    }

    @Override
    public void chooseFood(String name, String ndbNo) {
        Log.d(TAG, "User Selected " + name + " " + ndbNo);
        searchFoodView.showAddEditFood(name, ndbNo);


    }


    @Override
    public boolean isDataMissing() {

        return false;
    }

}
