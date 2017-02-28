package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.utdallas.csdesign.spring17.nutriscope.Injection;
import edu.utdallas.csdesign.spring17.nutriscope.R;

/**
 * Created by john on 2/10/17.
 */

public class OverviewActivity extends AppCompatActivity {

    private OverviewPresenter overviewPresenter;

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
                Injection.provideHistoryRepository(getApplicationContext()), fragment);

    }

}
