package edu.utdallas.csdesign.spring17.nutriscope.searchfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.NutriscopeApplication;
import edu.utdallas.csdesign.spring17.nutriscope.R;

/**
 * Created by john on 3/5/17.
 */

public class SearchFoodActivity extends AppCompatActivity {

    public static final String EXTRA_FOOD_ID = "FOOD_ID";


    @Inject SearchFoodPresenter searchFoodPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_single_fragment);

        String foodId = getIntent().getStringExtra(EXTRA_FOOD_ID);

        SearchFoodFragment searchFoodFragment =
                (SearchFoodFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);

        if (searchFoodFragment == null) {
            searchFoodFragment = SearchFoodFragment.newInstance();

            if (getIntent().hasExtra(EXTRA_FOOD_ID)) {
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_FOOD_ID, foodId);
                searchFoodFragment.setArguments(bundle);
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, searchFoodFragment)
                    .commit();
        }

        DaggerSearchFoodComponent.builder()
                .netComponent(((NutriscopeApplication) getApplication()).getNetComponent())
                .foodRepositoryComponent(
                        ((NutriscopeApplication) getApplication()).getFoodRepositoryComponent())
                .searchFoodPresenterModule(new SearchFoodPresenterModule(searchFoodFragment))
                .build().inject(this);


    }

}

