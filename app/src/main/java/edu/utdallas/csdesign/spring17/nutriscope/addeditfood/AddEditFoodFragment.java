package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.utdallas.csdesign.spring17.nutriscope.FoodNutrients;
import edu.utdallas.csdesign.spring17.nutriscope.R;

/**
 * Created by john on 2/20/17.
 */

public class AddEditFoodFragment extends Fragment implements AddEditFoodContract.View {

    private AddEditFoodContract.Presenter presenter;

    @BindView(R.id.foodname) EditText editTextFoodName;
    @BindView(R.id.foodEnergy) EditText editTextEnergy;
    @BindView(R.id.foodNutrientFat) EditText editTextFat;
    @BindView(R.id.foodNutrientProtein) EditText editTextProtein;
    @BindView(R.id.foodNutrientCarbohydrate) EditText editTextCarb;

    @BindView(R.id.fab) FloatingActionButton fab;

    public AddEditFoodFragment() {

    }

    public static AddEditFoodFragment newInstance() { return new AddEditFoodFragment(); }

    @Override
    public void setPresenter(AddEditFoodContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_add_edit_food, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_food, container, false);
        ButterKnife.bind(getActivity());

        ArrayList<FoodNutrients> defaultNutrients = Lists.newArrayList(FoodNutrients.CALORIE, FoodNutrients.FAT, FoodNutrients.PROTEIN);

        ImmutableMap<FoodNutrients, EditText> editTextBoxes = ImmutableMap.<FoodNutrients, EditText>builder()
                .put(FoodNutrients.CALORIE, editTextEnergy)
                .put(FoodNutrients.FAT, editTextFat)
                .put(FoodNutrients.PROTEIN, editTextProtein)
                .put(FoodNutrients.CARBOHYDRATE, editTextCarb)
                .build();

        List<EditText> activeTextEdits = new ArrayList<>();
        for (FoodNutrients nutrient: defaultNutrients) {
            activeTextEdits.add(editTextBoxes.get(nutrient));
        }

        ButterKnife.apply(activeTextEdits, ChangeVisibility, View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addFood();
            }
        });

        return view;
    }

    static final ButterKnife.Setter<View, Integer> ChangeVisibility
            = new ButterKnife.Setter<View, Integer>() {
        @Override
        public void set(View view, Integer value, int index) {
            view.setVisibility(value);
        }
    };


    @Override
    public void showFood(String name, String protein, String fat, String carb) {
        editTextFoodName.setText(name);


    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
