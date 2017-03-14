package edu.utdallas.csdesign.spring17.nutriscope;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.utdallas.csdesign.spring17.nutriscope.login.LoginActivity;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class OverviewActivity extends AppCompatActivity {

    private EditText tName;
    private EditText tAge;
    private TextView log;
    private Button bSave, bRefresh, bDelete;
    private Realm realm;
    private boolean logoutAfterClose;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tName = (EditText) findViewById(R.id.name);
        tAge = (EditText) findViewById(R.id.Age);
        log = (TextView) findViewById(R.id.log);
        bSave = (Button) findViewById(R.id.save);
        bRefresh = (Button) findViewById(R.id.refresh);
        bDelete = (Button) findViewById(R.id.delete);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB(tName.getText().toString().trim(), Integer.parseInt(tAge.getText().toString().trim()));

            }
        });

        bRefresh.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                refreshView();
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!tName.getText().toString().equals("")) {
                    deletePerson(tName.getText().toString().trim());
                }
            }
        });
    }



    private void saveToDB(final String name, final int age)
    {

    }

    //read from db, show to textview log
    private void refreshView()
    {
        RealmResults<Person> result = realm.where(Person.class).findAll();
        String output = "";
        for(Person person: result)
        {
            output+= person.toString() + "\n";
        }

        log.setText(output);
    }

    private void deletePerson(final String name) {


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(OverviewActivity.this, LoginActivity.class));
                finish();
                return true;
            case R.id.profile_settings:
                startActivity(new Intent(OverviewActivity.this, ProfileSettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

