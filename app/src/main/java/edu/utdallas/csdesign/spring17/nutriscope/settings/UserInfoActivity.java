package edu.utdallas.csdesign.spring17.nutriscope.settings;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.firebase.User;


public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button editNicknameB;
    private Button editAgeB;
    private Button editSexB;
    private Button editHeightB;
    private Button goBackB;
    private TextView textNickname;
    private TextView textAge;
    private TextView textSex;
    private TextView textHeight;
    private String sex, ftTxt,inTxt;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        setTitle("User Information");

        editNicknameB = (Button) findViewById(R.id.user_settings_changenickname);
        editAgeB = (Button) findViewById(R.id.user_settings_changeage);
        editSexB = (Button) findViewById(R.id.user_settings_changesex);
        editHeightB = (Button) findViewById(R.id.user_settings_changeheight);
        goBackB = (Button) findViewById(R.id.go_back_button);
        refreshUserInfo();
        editNicknameB.setOnClickListener(this);
        editAgeB.setOnClickListener(this);
        editSexB.setOnClickListener(this);
        editHeightB.setOnClickListener(this);
        goBackB.setOnClickListener(this);

    }

    private void refreshUserInfo()
    {
        textNickname = (TextView) findViewById(R.id.user_settings_nickname);
        textAge = (TextView) findViewById(R.id.user_settings_age);
        textSex = (TextView) findViewById(R.id.user_settings_sex);
        textHeight = (TextView) findViewById(R.id.user_settings_height);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if(!TextUtils.isEmpty(user.getNickname()))
                {
                    textNickname.setText(user.getNickname());
                }
                if(!TextUtils.isEmpty(user.getAge()))
                {
                    textAge.setText(user.getAge());
                }
                if(!TextUtils.isEmpty(user.getSex()))
                {
                    textSex.setText(user.getSex());
                }
                if(!TextUtils.isEmpty(user.getHeight()))
                {
                    textHeight.setText(user.getHeight());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("SETTING", "loadPost:onCancelled", databaseError.toException());
            }
        };
        db.addListenerForSingleValueEvent(userListener);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.user_settings_changenickname:
                handleChangeNickname();
                break;
            case R.id.user_settings_changeage:
                handleChangeAge();
                break;
            case R.id.user_settings_changesex:
                handleChangeSex();
                break;
            case R.id.user_settings_changeheight:
                handleChangeHeight();
                break;
            case R.id.go_back_button:
                startActivity(new Intent(this, ProfileSettingsActivity.class));
                finish();
                break;
        }
    }

    private void handleChangeNickname() {
        LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_settings, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        builder.setView(promptView);

        TextView title = (TextView) promptView.findViewById(R.id.input_dialog_text_msg);
        title.setText("Enter New Nickname:");
        final EditText et = (EditText) promptView.findViewById(R.id.edittext_input_dialog);


        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nick = et.getText().toString().trim();
                if(nick.length()>18)
                {
                    onErrorResponse("Nickname cannot exceed 18 characters");
                }
                else if(!TextUtils.isEmpty(nick))
                {
                    db.child("nickname").setValue(nick);
                    refreshUserInfo();
                }

                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void handleChangeAge() {
        LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_settings, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        builder.setView(promptView);

        TextView title = (TextView) promptView.findViewById(R.id.input_dialog_text_msg);
        title.setText("Enter Age:");
        final EditText et = (EditText) promptView.findViewById(R.id.edittext_input_dialog);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String age = et.getText().toString().trim();
                if(!isNumeric(age) || age.length() > 3)
                {
                    onErrorResponse("Please enter a valid number");
                }
                else if(!TextUtils.isEmpty(age))
                {
                    db.child("age").setValue(age);
                    refreshUserInfo();
                }


                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void handleChangeSex() {
        LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_sex, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        builder.setView(promptView);

        Spinner dropdown = (Spinner)promptView.findViewById(R.id.sex_spinner);
        String[] items = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
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
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.child("sex").setValue(sex);
                refreshUserInfo();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void handleChangeHeight() {
        LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_height, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        builder.setView(promptView);

        final NumberPicker ft = (NumberPicker) promptView.findViewById(R.id.height_ft);
        final NumberPicker in = (NumberPicker) promptView.findViewById(R.id.height_in);
        ft.setMinValue(0);
        ft.setMaxValue(10);
        in.setMinValue(0);
        in.setMaxValue(12);
        ft.setWrapSelectorWheel(true);
        in.setWrapSelectorWheel(true);
        ftTxt = "0";
        inTxt = "0";
        ft.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){

                ftTxt = newVal + "";
            }
        });

        in.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                inTxt = newVal + "";
            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String height = ftTxt + "'" + inTxt + "\"";
                db.child("height").setValue(height);
                refreshUserInfo();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void onErrorResponse(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNumeric(String string) {
        try {
            Long.parseLong(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
