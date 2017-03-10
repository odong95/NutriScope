package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.Injection;
import edu.utdallas.csdesign.spring17.nutriscope.Injector;
import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;

/**
 * Created by john on 2/10/17.
 */

public class OverviewActivity extends AppCompatActivity {

    private OverviewPresenter overviewPresenter;

    @Inject
    Repository repository;

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


        // Create the presenter
        overviewPresenter = new OverviewPresenter(
                repository, fragment);

    }

}
