package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.NutriscopeApplication;
import edu.utdallas.csdesign.spring17.nutriscope.R;

/**
 *

 *
 */

public class OverviewActivity extends AppCompatActivity {

    public static final String EXTRA_CONSUMED_FOOD_KEY = "CONSUMED_FOOD_KEY";

    @Inject OverviewPresenter overviewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_fragment);

        OverviewFragment fragment = (OverviewFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = OverviewFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }


        DaggerOverviewComponent.builder()
                .historyRepositoryComponent(((NutriscopeApplication) getApplication()).getHistoryRepositoryComponent())
                .userManagerComponent(((NutriscopeApplication) getApplication()).getUserManagerComponent())
                .nutritionRepositoryComponent(((NutriscopeApplication) getApplication()).getNutritionRepositoryComponent())
                .overviewModule(new OverviewModule(fragment))
                .build()
                .inject(this);
    }

}
