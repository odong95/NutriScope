package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.Injector;
import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.HistoryRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;

/**
 *

 *
 */

public class OverviewActivity extends AppCompatActivity {

    public static final String EXTRA_CONSUMED_FOOD_KEY = "CONSUMED_FOOD_KEY";

    private OverviewPresenter overviewPresenter;

    @Inject
    HistoryRepository historyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.get().inject(this);


        setContentView(R.layout.activity_single_fragment);

        OverviewFragment fragment = (OverviewFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = OverviewFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }


        // Create the presenter
        overviewPresenter = new OverviewPresenter(fragment, historyRepository);

        fragment.setPresenter(overviewPresenter);

    }

}
