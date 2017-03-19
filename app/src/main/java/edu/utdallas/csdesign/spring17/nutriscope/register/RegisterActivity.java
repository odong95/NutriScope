package edu.utdallas.csdesign.spring17.nutriscope.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.R;


public class RegisterActivity extends AppCompatActivity {

    @Inject RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        RegisterFragment fragment = RegisterFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_register, fragment)
                .commit();

        DaggerRegisterComponent.builder()
                .registerPresenterModule(
                        new RegisterPresenterModule(fragment, new RegisterInteractor()))
                .build().inject(this);
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