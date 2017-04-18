package edu.utdallas.csdesign.spring17.nutriscope.settings;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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


import java.util.Calendar;

import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.firebase.User;
import edu.utdallas.csdesign.spring17.nutriscope.login.LoginActivity;


public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button editNicknameB;
    private Button editAgeB;
    private Button editSexB;
    private Button editHeightB;
    private Button editWeightB;
    private Button editActivityLvlB;
    private Button editCalorieGoalB;
    private Button getRecommendCalB;
    private Toolbar toolbar;
    private TextView textNickname;
    private TextView textAge;
    private TextView textSex;
    private TextView textHeight;
    private TextView textWeight;
    private TextView textActivityLevel;
    private TextView textCalorieGoal;
    private String sex, ftTxt,inTxt;
    private String activityLvl;
    private TEECalculator calc;
    private boolean clicked;
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
        editWeightB = (Button) findViewById(R.id.user_settings_changeweight);
        editActivityLvlB = (Button) findViewById(R.id.user_settings_changeActivityLevel);
        editCalorieGoalB = (Button) findViewById(R.id.user_settings_changecalories);
        getRecommendCalB = (Button) findViewById(R.id.getRecGoal);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        calc = new TEECalculator();
        refreshUserInfo();

        editNicknameB.setOnClickListener(this);
        editAgeB.setOnClickListener(this);
        editSexB.setOnClickListener(this);
        editHeightB.setOnClickListener(this);
        editWeightB.setOnClickListener(this);
        editActivityLvlB.setOnClickListener(this);
        editCalorieGoalB.setOnClickListener(this);
        getRecommendCalB.setOnClickListener(this);


    }

    private void refreshUserInfo() {
        textNickname = (TextView) findViewById(R.id.user_settings_nickname);
        textAge = (TextView) findViewById(R.id.user_settings_age);
        textSex = (TextView) findViewById(R.id.user_settings_sex);
        textHeight = (TextView) findViewById(R.id.user_settings_height);
        textWeight = (TextView) findViewById(R.id.user_settings_weight);
        textActivityLevel = (TextView) findViewById(R.id.user_settings_activityLevel);
        textCalorieGoal = (TextView) findViewById(R.id.user_settings_calories);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (!TextUtils.isEmpty(user.getNickname())) {
                    textNickname.setText(user.getNickname());
                }
                if (!TextUtils.isEmpty(user.getAge())) {
                    textAge.setText(user.getAge());
                    calc.setAge(user.getAge());
                }
                if (!TextUtils.isEmpty(user.getSex())) {
                    textSex.setText(user.getSex());
                    calc.setGender(user.getSex());
                }
                if (!TextUtils.isEmpty(user.getHeight())) {
                    textHeight.setText(user.getHeight());
                    calc.setHeight(user.getHft(),user.getHin());
                }
                if(!TextUtils.isEmpty(user.getWeight()))
                {
                    textWeight.setText(user.getWeight());
                    calc.setWeight(user.getWeight());
                }
                if(!TextUtils.isEmpty(user.getActivityLevel()))
                {
                    textActivityLevel.setText(parseLvl(user.getActivityLevel()));
                    calc.setActivityLevel(user.getActivityLevel());
                }
                if(!TextUtils.isEmpty(user.getCalorieGoal()))
                {
                    textCalorieGoal.setText(user.getCalorieGoal());
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
            case R.id.user_settings_changeweight:
                handleChangeWeight();
                break;
            case R.id.user_settings_changeActivityLevel:
                handleChangeActivityLevel();
                break;
            case R.id.user_settings_changecalories:
                handleChangeCalorieGoal();
                break;
            case R.id.getRecGoal:
                clicked = true;
                getCalorieGoal();
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(this, ProfileSettingsActivity.class));
        finish();
        return true;
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
                if (nick.length() > 18) {
                    onErrorResponse("Nickname cannot exceed 18 characters");
                } else if (!TextUtils.isEmpty(nick)) {
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
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String age = getAge(year,monthOfYear,dayOfMonth);
                db.child("age").setValue(age);
                refreshUserInfo();
            }

        };

        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        dialog.setTitle("Enter Date of Birth:");
        dialog.show();


    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    private void handleChangeSex() {
        LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_sex, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        builder.setView(promptView);

        Spinner dropdown = (Spinner) promptView.findViewById(R.id.sex_spinner);
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

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String height = ftTxt + "'" + inTxt + "\"";
                db.child("height").setValue(height);
                db.child("hft").setValue(ftTxt);
                db.child("hin").setValue(inTxt);
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

    private void handleChangeWeight()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_settings, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        builder.setView(promptView);

        TextView title = (TextView) promptView.findViewById(R.id.input_dialog_text_msg);
        title.setText("Enter Weight (lbs):");
        final EditText et = (EditText) promptView.findViewById(R.id.edittext_input_dialog);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String weight = et.getText().toString().trim();
                if(!isNumeric(weight) || weight.length() > 3)
                {
                    onErrorResponse("Please enter a valid number");
                }
                else if(!TextUtils.isEmpty(weight))
                {
                    db.child("weight").setValue(weight);
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
    private void handleChangeActivityLevel() {
        LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_sex, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        builder.setView(promptView);

        Spinner dropdown = (Spinner)promptView.findViewById(R.id.sex_spinner);
        String[] items = new String[]{"Sedentary - little to no exercise",
                                      "Lightly Active - exercise/sports 1-3 times/week",
                                      "Moderately Active - exercise/sports 3-5 times/week",
                                      "Very Active - exercise/sports 6-7 times/week",
                                      "Extra Active - very hard exercise/sports or physical job",};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
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
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.child("activityLevel").setValue(activityLvl);
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

    private void handleChangeCalorieGoal() {
        LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog_settings_goal, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        builder.setView(promptView);

        TextView title = (TextView) promptView.findViewById(R.id.input_dialog_text_msg);
        title.setText("Enter Daily Calorie Goal:");
        final EditText et = (EditText) promptView.findViewById(R.id.edittext_input_dialog);
        Button recGoal = (Button) promptView.findViewById(R.id.getRecGoal);
        recGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calc.isFilled())
                {
                    et.setText(calc.getTEE());
                }
                else
                {
                    onErrorResponse("Please fill out all previous fields (gender, age, weight, height)");
                }

            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String goal = et.getText().toString().trim();
                if(!isNumeric(goal) || goal.length() > 4)
                {
                    onErrorResponse("Please enter a valid number");
                }
                else if(!TextUtils.isEmpty(goal))
                {
                    db.child("calorieGoal").setValue(goal);
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

    private void getCalorieGoal()
    {
        if(calc.isFilled())
        {
            db.child("calorieGoal").setValue(calc.getTEE());
            refreshUserInfo();
        }
        else
        {
            if(clicked) {
                onErrorResponse("Please fill out all previous fields (sex, age, weight, height)");
            }
        }

        clicked = false;
    }


    private String parseLvl(String lvl)
    {
        if(lvl.equals("1.2"))
        {
            return "Sedentary";
        }
        if(lvl.equals("1.3"))
        {
            return "Lightly Active";
        }
        if(lvl.equals("1.5"))
        {
            return "Moderately Active";
        }
        if(lvl.equals("1.7"))
        {
            return "Very Active";
        }
        else
        {
            return "Extra Active";
        }

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
