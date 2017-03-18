package edu.utdallas.csdesign.spring17.nutriscope.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.utdallas.csdesign.spring17.nutriscope.R;


public class LoginActivity extends AppCompatActivity {


    private LoginPresenter loginPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        LoginFragment fragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_login, fragment)
                .commit();

        LoginInteractor interactor = new LoginInteractor();
        loginPresenter = new LoginPresenter(interactor, fragment);

        interactor.setPresenter(loginPresenter);
        fragment.setPresenter(loginPresenter);
        fragment.setDialog(mProgressDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}