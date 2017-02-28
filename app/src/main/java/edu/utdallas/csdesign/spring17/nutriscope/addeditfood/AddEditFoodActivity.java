package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.utdallas.csdesign.spring17.nutriscope.R;

/**
 * Created by john on 2/21/17.
 */

public class AddEditFoodActivity extends AppCompatActivity {

    public static final String EXTRA_FOOD_ID = "FOOD_ID";

    private AddEditFoodPresenter addEditFoodPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_fragment);

        String foodId = getIntent().getStringExtra(EXTRA_FOOD_ID);

        AddEditFoodFragment addEditFoodFragment = (AddEditFoodFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (addEditFoodFragment == null) {
            addEditFoodFragment = AddEditFoodFragment.newInstance();

            if (getIntent().hasExtra(EXTRA_FOOD_ID)) {
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_FOOD_ID, foodId);
                addEditFoodFragment.setArguments(bundle);
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, addEditFoodFragment)
                    .commit();
        }

        addEditFoodPresenter = new AddEditFoodPresenter();

    }

}
