package edu.utdallas.csdesign.spring17.nutriscope.settings.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.utdallas.csdesign.spring17.nutriscope.R;

import static edu.utdallas.csdesign.spring17.nutriscope.settings.UserInfoActivity.isNumeric;

/**
 * Created by john on 4/30/17.
 */

public class SettingsDialogFragment extends DialogFragment {

    private final static String TAG = "SettingsDialog";

    public final static String SETTINGS_TYPE = "TYPE";
    public final static String NICKNAME = "NICKNAME";
    public final static String CALORIE_GOAL = "CALORIE_GOAL";
    public final static String AGE = "AGE";
    public final static String SEX = "SEX";
    public final static String HEIGHT = "HEIGHT";
    public final static String WEIGHT = "WEIGHT";
    public final static String ACTIVITY_LEVEL = "ACTIVITY_LEVEL";



    public final static String SETTINGS_VIEW = "VIEW";
    public final static String SETTINGS_MSG = "MSG";

    public interface SettingsDialogListener {
        void OnDialogSetBundle(Bundle bundle);
    }

    private SettingsDialogListener listener;

    public static SettingsDialogFragment newInstance(Bundle args) {
        SettingsDialogFragment dialog = new SettingsDialogFragment();

        dialog.setArguments(args);

        return dialog;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SettingsDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }
    }

    String sex;
    String ftTxt = "0";
    String inTxt = "0";
    String activityLvl;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        String type = getArguments().getString(SETTINGS_TYPE);

        try {
            switch (type) {
                case NICKNAME:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View view = inflater.inflate(R.layout.input_dialog_settings, null);

                    final TextView title = (TextView) view.findViewById(R.id.input_dialog_text_msg);
                    title.setText(R.string.nickname);
                    final EditText input = (EditText) view.findViewById(R.id.edittext_input_dialog);

                    builder.setView(view)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Bundle bundle = new Bundle();
                            bundle.putString(SETTINGS_TYPE, NICKNAME);
                            bundle.putString(SETTINGS_MSG, input.getText().toString());
                            Log.d(TAG, "OK " + input.getText().toString());
                            listener.OnDialogSetBundle(bundle);

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SettingsDialogFragment.this.getDialog().cancel();
                    }
                    }).create();
                    return builder.create();

                case CALORIE_GOAL:
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater2 = getActivity().getLayoutInflater();
                    View view2 = inflater2.inflate(R.layout.input_dialog_settings, null);

                    final TextView title2 = (TextView) view2.findViewById(R.id.input_dialog_text_msg);
                    title2.setText(R.string.calorie_goal);
                    final EditText input2 = (EditText) view2.findViewById(R.id.edittext_input_dialog);

                    builder2.setView(view2)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(SETTINGS_TYPE, CALORIE_GOAL);
                                    bundle.putInt(SETTINGS_MSG, Integer.parseInt(input2.getText().toString()));
                                    Log.d(TAG, "OK " + input2.getText().toString());
                                    listener.OnDialogSetBundle(bundle);

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SettingsDialogFragment.this.getDialog().cancel();
                        }
                    }).create();
                    return builder2.create();

                case SEX:
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater3 = getActivity().getLayoutInflater();
                    View view3 = inflater3.inflate(R.layout.input_dialog_sex, null);

                    Spinner dropdown = (Spinner) view3.findViewById(R.id.sex_spinner);
                    String[] items = new String[]{"Male", "Female"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
                    dropdown.setAdapter(adapter);

                    dropdown.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            sex = (String) parent.getItemAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    }));

                    builder3.setView(view3)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(SETTINGS_TYPE, SEX);
                                    bundle.putString(SETTINGS_MSG, sex);
                                    Log.d(TAG, "OK " + sex);
                                    listener.OnDialogSetBundle(bundle);

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SettingsDialogFragment.this.getDialog().cancel();
                        }
                    }).create();
                    return builder3.create();

                case AGE:
                    AlertDialog.Builder builder4 = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater4 = getActivity().getLayoutInflater();
                    View view4 = inflater4.inflate(R.layout.input_dialog_settings, null);

                    final TextView title4 = (TextView) view4.findViewById(R.id.input_dialog_text_msg);
                    title4.setText(R.string.age);
                    final EditText input4 = (EditText) view4.findViewById(R.id.edittext_input_dialog);

                    builder4.setView(view4)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(SETTINGS_TYPE, AGE);
                                    bundle.putString(SETTINGS_MSG, input4.getText().toString());
                                    Log.d(TAG, "OK " + input4.getText().toString());
                                    listener.OnDialogSetBundle(bundle);

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SettingsDialogFragment.this.getDialog().cancel();
                        }
                    }).create();
                    return builder4.create();

                case WEIGHT:
                    AlertDialog.Builder builder5 = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater5 = getActivity().getLayoutInflater();
                    View view5 = inflater5.inflate(R.layout.input_dialog_settings, null);

                    final TextView title5 = (TextView) view5.findViewById(R.id.input_dialog_text_msg);
                    title5.setText(R.string.weight);
                    final EditText input5 = (EditText) view5.findViewById(R.id.edittext_input_dialog);

                    builder5.setView(view5)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    String weight = input5.getText().toString().trim();
                                    if(!isNumeric(weight) || weight.length() > 3)
                                    {
                                        onErrorResponse("Please enter a valid number");
                                    }
                                    else if(!TextUtils.isEmpty(weight))
                                    {
                                        Bundle bundle = new Bundle();
                                        bundle.putString(SETTINGS_TYPE, WEIGHT);
                                        bundle.putString(SETTINGS_MSG, input5.getText().toString());
                                        Log.d(TAG, "OK " + input5.getText().toString());
                                        listener.OnDialogSetBundle(bundle);
                                    }

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SettingsDialogFragment.this.getDialog().cancel();
                        }
                    }).create();
                    return builder5.create();

                case HEIGHT:
                    AlertDialog.Builder builder6 = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater6 = getActivity().getLayoutInflater();
                    View view6 = inflater6.inflate(R.layout.input_dialog_height, null);

                    final TextView title6 = (TextView) view6.findViewById(R.id.input_dialog_text_msg);
                    title6.setText(R.string.height);
                    final EditText input6 = (EditText) view6.findViewById(R.id.edittext_input_dialog);

                    final NumberPicker ft = (NumberPicker) view6.findViewById(R.id.height_ft);
                    final NumberPicker in = (NumberPicker) view6.findViewById(R.id.height_in);
                    ft.setMinValue(0);
                    ft.setMaxValue(10);
                    in.setMinValue(0);
                    in.setMaxValue(12);
                    ft.setWrapSelectorWheel(true);
                    in.setWrapSelectorWheel(true);

                    ft.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                            ftTxt = newVal + "";
                        }
                    });

                    in.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            inTxt = newVal + "";
                        }
                    });


                    builder6.setView(view6)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Bundle bundle = new Bundle();
                                    bundle.putString(SETTINGS_TYPE, HEIGHT);
                                    bundle.putString(SETTINGS_MSG, ftTxt + " " + inTxt);
                                    Log.d(TAG, "OK " + ftTxt + " " + inTxt);
                                    listener.OnDialogSetBundle(bundle);


                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SettingsDialogFragment.this.getDialog().cancel();
                        }
                    }).create();
                    return builder6.create();
                case ACTIVITY_LEVEL:

                    LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                    final View promptView = layoutInflater.inflate(R.layout.input_dialog_sex, null);
                    AlertDialog.Builder builder7 = new AlertDialog.Builder(getActivity());
                    builder7.setView(promptView);

                    Spinner dropdown2 = (Spinner)promptView.findViewById(R.id.sex_spinner);
                    String[] items2 = new String[]{"Sedentary - little to no exercise",
                            "Lightly Active - exercise/sports 1-3 times/week",
                            "Moderately Active - exercise/sports 3-5 times/week",
                            "Very Active - exercise/sports 6-7 times/week",
                            "Extra Active - very hard exercise/sports or physical job",};
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_spinner_item, items2);
                    dropdown2.setAdapter(adapter2);

                    dropdown2.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            String s = (String) parent.getItemAtPosition(position);
                            if(s.charAt(0) == 'S')
                            {
                                activityLvl = "1.2";
                            }
                            else if(s.charAt(0) == 'L')
                            {
                                activityLvl = "1.375";
                            }
                            else if(s.charAt(0) == 'M')
                            {
                                activityLvl = "1.55";
                            }
                            else if(s.charAt(0) == 'V')
                            {
                                activityLvl = "1.7";
                            }
                            else if(s.charAt(0) == 'E')
                            {
                                activityLvl = "1.9";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    }));


                    builder7.setView(promptView)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Bundle bundle = new Bundle();
                                    bundle.putString(SETTINGS_TYPE, ACTIVITY_LEVEL);
                                    bundle.putString(SETTINGS_MSG, activityLvl);
                                    Log.d(TAG, "OK " + activityLvl);
                                    listener.OnDialogSetBundle(bundle);


                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SettingsDialogFragment.this.getDialog().cancel();
                        }
                    }).create();
                    return builder7.create();

            }

        } catch(NullPointerException ex) {
            ex.printStackTrace();
        }


        return super.onCreateDialog(savedInstanceState);
    }


    public void onErrorResponse(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        super.setupDialog(dialog, style);
    }
}
