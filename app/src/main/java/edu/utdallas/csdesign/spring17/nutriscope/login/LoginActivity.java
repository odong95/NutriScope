package edu.utdallas.csdesign.spring17.nutriscope.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.R;


public class LoginActivity extends AppCompatActivity {


    @Inject LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        LoginFragment fragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_login, fragment)
                .commit();

        DaggerLoginComponent.builder()
                .loginPresenterModule(new LoginPresenterModule(fragment, new LoginInteractor()))
                .build()
                .inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }



}