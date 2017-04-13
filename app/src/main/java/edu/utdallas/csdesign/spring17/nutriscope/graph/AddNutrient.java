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

import edu.utdallas.csdesign.spring17.nutriscope.R;

public class AddNutrient implements View.OnClickListener{
    private SharedPreferences sharedPref;
    private Activity activity;
    private GraphRecyclerViewAdapter adapter;
    private ArrayList<String> list;
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
        promptView.setOnClickListener(this);
        final CheckBox calories = (CheckBox) promptView.findViewById(R.id.caloriesBox);
        calories.setChecked(sharedPref.getBoolean("calories",false));
        final CheckBox carbs = (CheckBox) promptView.findViewById(R.id.carbsBox);
        carbs.setChecked(sharedPref.getBoolean("carbohydrates",false));
        final CheckBox protein = (CheckBox) promptView.findViewById(R.id.proteinBox);
        protein.setChecked(sharedPref.getBoolean("protein",false));
        final CheckBox fat = (CheckBox) promptView.findViewById(R.id.fatBox);
        fat.setChecked(sharedPref.getBoolean("fat",false));
        final CheckBox box1 = (CheckBox) promptView.findViewById(R.id.Box1);
        box1.setChecked(sharedPref.getBoolean("box1",false));
        final CheckBox box2 = (CheckBox) promptView.findViewById(R.id.Box2);
        box2.setChecked(sharedPref.getBoolean("box2",false));

        calories.setOnClickListener(this);
        carbs.setOnClickListener(this);
        protein.setOnClickListener(this);
        fat.setOnClickListener(this);
        box1.setOnClickListener(this);
        box2.setOnClickListener(this);
        adapter.clear();
        list = new ArrayList<String>();
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(calories.isChecked() && !contains("Calories"))
                {
                    list.add("Calories");
                }
                if (carbs.isChecked() && !contains("Carbohydrates"))
                {
                    list.add("Carbohydrates");
                }
                if (protein.isChecked() && !contains("Protein") )
                {
                    list.add("Protein");
                }
                if (fat.isChecked() && !contains("Fat"))
                {
                    list.add("Fat");
                }
                if (box1.isChecked() && !contains("Box1"))
                {
                    list.add("Box1");
                }
                if (box2.isChecked() && !contains("Box2"))
                {
                    list.add("Box2");
                }
                Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
                for(String s: list)
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
                editor.putBoolean("calories", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.carbsBox:
                editor.putBoolean("carbohydrates", ((CheckBox) view).isChecked());
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
            case R.id.Box1:
                editor.putBoolean("box1", ((CheckBox) view).isChecked());
                editor.commit();
                break;
            case R.id.Box2:
                editor.putBoolean("box2", ((CheckBox) view).isChecked());
                editor.commit();
                break;

        }
    }

    private boolean contains(String s)
    {
        if(list.contains(s))
        {
            return true;
        }
        return false;
    }

}
