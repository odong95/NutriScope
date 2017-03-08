package edu.utdallas.csdesign.spring17.nutriscope.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import edu.utdallas.csdesign.spring17.nutriscope.R;


public class LoginActivity extends AppCompatActivity {

    public static final String ACTION_IGNORE_CURRENT_USER = "action.ignoreCurrentUser";
    public LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment fragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_login, fragment)
                .commit();

        loginPresenter = new LoginPresenter(fragment);

    }


    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
