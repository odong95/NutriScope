package edu.utdallas.csdesign.spring17.nutriscope;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Person person = bgRealm.createObject(Person.class);
                person.setName(name);
                person.setAge(age);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                System.out.println("saved to db");
                refreshView();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                System.out.println("Error, not saved to db");
            }
        });
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

        final RealmResults<Person> result = realm.where(Person.class)
                .equalTo("name", name)
                .findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // remove single match
                result.deleteLastFromRealm();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }



    @Override
    protected void onStop() {
        realm.removeAllChangeListeners();
        realm.close();
        realm = null;
        if (logoutAfterClose) {
            UserManager.logoutActiveUser();
            logoutAfterClose = false;
        }

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
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setAction(LoginActivity.ACTION_IGNORE_CURRENT_USER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                logoutAfterClose = true;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

