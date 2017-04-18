package edu.utdallas.csdesign.spring17.nutriscope.graph;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.FoodNutrients;

public class AddNutrient implements View.OnClickListener{
    private SharedPreferences sharedPref;
    private Activity activity;
    private GraphRecyclerViewAdapter adapter;
    private ArrayList<FoodNutrients> list;

    public AddNutrient(SharedPreferences sharedPref, Activity act,GraphRecyclerViewAdapter adapter)
    {

        this.activity = act;
        this.adapter = adapter;
        setup(sharedPref);

    }

    private void setup(SharedPreferences sf)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_nutrients, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(promptView);
        sharedPref = sf;

        final CheckBox calcium = (CheckBox) promptView.findViewById(R.id.calciumBox);
        calcium.setChecked(sharedPref.getBoolean("calcium",false));
        final CheckBox calories = (CheckBox) promptView.findViewById(R.id.caloriesBox);
        calories.setChecked(sharedPref.getBoolean("calorie",false));
        final CheckBox carbs = (CheckBox) promptView.findViewById(R.id.carbsBox);
        carbs.setChecked(sharedPref.getBoolean("carbohydrate",false));
        final CheckBox cholesterol = (CheckBox) promptView.findViewById(R.id.cholesterolBox);
        cholesterol.setChecked(sharedPref.getBoolean("cholesterol",false));
        final CheckBox fat = (CheckBox) promptView.findViewById(R.id.fatBox);
        fat.setChecked(sharedPref.getBoolean("fat",false));
        final CheckBox fiber = (CheckBox) promptView.findViewById(R.id.fiberBox);
        fiber.setChecked(sharedPref.getBoolean("fiber",false));
        final CheckBox folicAcid = (CheckBox) promptView.findViewById(R.id.folicBox);
        folicAcid.setChecked(sharedPref.getBoolean("folicAcid",false));
        final CheckBox iron = (CheckBox) promptView.findViewById(R.id.ironBox);
        iron.setChecked(sharedPref.getBoolean("iron",false));
        final CheckBox magnesium = (CheckBox) promptView.findViewById(R.id.magnesiumBox);
        magnesium.setChecked(sharedPref.getBoolean("magnesium",false));
        final CheckBox niacin = (CheckBox) promptView.findViewById(R.id.niacinBox);
        niacin.setChecked(sharedPref.getBoolean("niacin",false));
        final CheckBox phosphorus = (CheckBox) promptView.findViewById(R.id.phosBox);
        phosphorus.setChecked(sharedPref.getBoolean("phosphorus",false));
        final CheckBox potassium = (CheckBox) promptView.findViewById(R.id.potassiumBox);
        potassium.setChecked(sharedPref.getBoolean("potassium",false));
        final CheckBox protein = (CheckBox) promptView.findViewById(R.id.proteinBox);
        protein.setChecked(sharedPref.getBoolean("protein",false));
        final CheckBox riboflavin = (CheckBox) promptView.findViewById(R.id.riboBox);
        riboflavin.setChecked(sharedPref.getBoolean("riboflavin",false));
        final CheckBox selenium = (CheckBox) promptView.findViewById(R.id.seleniumBox);
        selenium.setChecked(sharedPref.getBoolean("selenium",false));
        final CheckBox sodium = (CheckBox) promptView.findViewById(R.id.sodiumBox);
        sodium.setChecked(sharedPref.getBoolean("sodium",false));
        final CheckBox sugar = (CheckBox) promptView.findViewById(R.id.sugarBox);
        sugar.setChecked(sharedPref.getBoolean("sugars",false));
        final CheckBox thiamin = (CheckBox) promptView.findViewById(R.id.thiaminBox);
        thiamin.setChecked(sharedPref.getBoolean("thiamin",false));
        final CheckBox vita = (CheckBox) promptView.findViewById(R.id.vitaBox);
        vita.setChecked(sharedPref.getBoolean("vitamin a",false));
        final CheckBox vitb6 = (CheckBox) promptView.findViewById(R.id.vitb6Box);
        vitb6.setChecked(sharedPref.getBoolean("vitamin b6",false));
        final CheckBox vitb12 = (CheckBox) promptView.findViewById(R.id.vitb12Box);
        vitb12.setChecked(sharedPref.getBoolean("vitamin b12",false));
        final CheckBox vitc = (CheckBox) promptView.findViewById(R.id.vitcBox);
        vitc.setChecked(sharedPref.getBoolean("vitamin c",false));
        final CheckBox vitd = (CheckBox) promptView.findViewById(R.id.vitdBox);
        vitd.setChecked(sharedPref.getBoolean("vitamin d",false));
        final CheckBox vitk = (CheckBox) promptView.findViewById(R.id.vitkBox);
        vitk.setChecked(sharedPref.getBoolean("vitamin k",false));
        final CheckBox zinc = (CheckBox) promptView.findViewById(R.id.zincBox);
        zinc.setChecked(sharedPref.getBoolean("zinc",false));

        calories.setOnClickListener(this);
        carbs.setOnClickListener(this);
        protein.setOnClickListener(this);
        fat.setOnClickListener(this);
        calcium.setOnClickListener(this);
        cholesterol.setOnClickListener(this);
        fiber.setOnClickListener(this);
        folicAcid.setOnClickListener(this);
        iron.setOnClickListener(this);
        magnesium.setOnClickListener(this);
        niacin.setOnClickListener(this);
        phosphorus.setOnClickListener(this);
        potassium.setOnClickListener(this);
        protein.setOnClickListener(this);
        riboflavin.setOnClickListener(this);
        selenium.setOnClickListener(this);
        sodium.setOnClickListener(this);
        sugar.setOnClickListener(this);
        thiamin.setOnClickListener(this);
        vita.setOnClickListener(this);
        vitb6.setOnClickListener(this);
        vitb12.setOnClickListener(this);
        vitc.setOnClickListener(this);
        vitd.setOnClickListener(this);
        vitk.setOnClickListener(this);
        zinc.setOnClickListener(this);

        adapter.clear();
        list = new ArrayList<FoodNutrients>();
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(calories.isChecked())
                {
                    list.add(FoodNutrients.CALORIE);
                }
                if (carbs.isChecked())
                {
                    list.add(FoodNutrients.CARBOHYDRATE);
                }
                if (protein.isChecked())
                {
                    list.add(FoodNutrients.PROTEIN);
                }
                if (fat.isChecked())
                {
                    list.add(FoodNutrients.FAT);
                }
                if (calcium.isChecked())
                {
                    list.add(FoodNutrients.CALCIUM);
                }
                if (cholesterol.isChecked())
                {
                    list.add(FoodNutrients.CHOLESTEROL);
                }
                if (fiber.isChecked())
                {
                    list.add(FoodNutrients.FIBER);
                }
                if (folicAcid.isChecked())
                {
                    list.add(FoodNutrients.FOLIC_ACID);
                }
                if (iron.isChecked())
                {
                    list.add(FoodNutrients.IRON);
                }
                if (magnesium.isChecked())
                {
                    list.add(FoodNutrients.MAGNESIUM);
                }
                if (niacin.isChecked())
                {
                    list.add(FoodNutrients.NIACIN);
                }
                if (phosphorus.isChecked())
                {
                    list.add(FoodNutrients.PHOSPHORUS);
                }
                if (potassium.isChecked())
                {
                    list.add(FoodNutrients.POTASSIUM);
                }
                if (riboflavin.isChecked())
                {
                    list.add(FoodNutrients.RIBOFLAVIN);
                }
                if (selenium.isChecked())
                {
                    list.add(FoodNutrients.SELENIUM);
                }
                if (sodium.isChecked())
                {
                    list.add(FoodNutrients.SODIUM);
                }
                if (sugar.isChecked())
                {
                    list.add(FoodNutrients.SUGARS);
                }
                if (thiamin.isChecked())
                {
                    list.add(FoodNutrients.THIAMIN);
                }
                if (vita.isChecked())
                {
                    list.add(FoodNutrients.VITAMIN_A);
                }
                if (vitb6.isChecked())
                {
                    list.add(FoodNutrients.VITAMIN_B6);
                }
                if (vitb12.isChecked())
                {
                    list.add(FoodNutrients.VITAMIN_B12);
                }
                if (vitc.isChecked())
                {
                    list.add(FoodNutrients.VITAMIN_C);
                }
                if (vitd.isChecked())
                {
                    list.add(FoodNutrients.VITAMIN_D);
                }
                if (vitk.isChecked())
                {
                    list.add(FoodNutrients.VITAMIN_K);
                }
                if (zinc.isChecked())
                {
                    list.add(FoodNutrients.ZINC);
                }
                Collections.sort(list, new Comparator<FoodNutrients>()
                {
                    @Override
                    public int compare(FoodNutrients o1, FoodNutrients o2)
                    {
                        return o1.getNutrientString().compareTo(o2.getNutrientString());
                    }
                });

                for(FoodNutrients s: list)
                {
                    adapter.add(s);
                }
                dialog.dismiss();
            }
        });

        builder.show();
    }



    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();

        switch(view.getId()) {
            case R.id.caloriesBox:
                editor.putBoolean("calorie", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.carbsBox:
                editor.putBoolean("carbohydrate", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.proteinBox:
                editor.putBoolean("protein", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.fatBox:
                editor.putBoolean("fat", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.calciumBox:
                editor.putBoolean("calcium", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.cholesterolBox:
                editor.putBoolean("cholesterol", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.fiberBox:
                editor.putBoolean("fiber", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.folicBox:
                editor.putBoolean("folic acid", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.ironBox:
                editor.putBoolean("iron", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.magnesiumBox:
                editor.putBoolean("magnesium", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.niacinBox:
                editor.putBoolean("niacin", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.phosBox:
                editor.putBoolean("phosphorus", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.potassiumBox:
                editor.putBoolean("potassium", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.riboBox:
                editor.putBoolean("riboflavin", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.seleniumBox:
                editor.putBoolean("selenium", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.sodiumBox:
                editor.putBoolean("sodium", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.sugarBox:
                editor.putBoolean("sugars", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.thiaminBox:
                editor.putBoolean("thiamin", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.vitaBox:
                editor.putBoolean("vitamin a", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.vitb6Box:
                editor.putBoolean("vitamin b6", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.vitb12Box:
                editor.putBoolean("vitamin b12", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.vitcBox:
                editor.putBoolean("vitamin c", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.vitdBox:
                editor.putBoolean("vitamin d", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.vitkBox:
                editor.putBoolean("vitamin k", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.zincBox:
                editor.putBoolean("zinc", ((CheckBox) view).isChecked());
                editor.commit();
                break;

        }

    }



}
