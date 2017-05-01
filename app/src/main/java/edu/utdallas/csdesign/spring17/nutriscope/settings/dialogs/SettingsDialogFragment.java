package edu.utdallas.csdesign.spring17.nutriscope.settings.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.utdallas.csdesign.spring17.nutriscope.R;

/**
 * Created by john on 4/30/17.
 */

public class SettingsDialogFragment extends DialogFragment {

    private final static String TAG = "SettingsDialog";

    public final static String SETTINGS_TYPE = "TYPE";
    public final static String NICKNAME = "NICKNAME";
    public final static String CALORIE_GOAL = "CALORIE_GOAL";

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


            }

        } catch(NullPointerException ex) {
            ex.printStackTrace();
        }


        return super.onCreateDialog(savedInstanceState);
    }






}
