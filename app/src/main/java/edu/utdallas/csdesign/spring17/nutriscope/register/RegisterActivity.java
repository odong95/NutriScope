package edu.utdallas.csdesign.spring17.nutriscope.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import edu.utdallas.csdesign.spring17.nutriscope.OverviewActivity;
import edu.utdallas.csdesign.spring17.nutriscope.R;
import edu.utdallas.csdesign.spring17.nutriscope.login.LoginActivity;


public class RegisterActivity extends AppCompatActivity {

    public RegisterPresenter registerPresenter;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;
    public boolean fbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    hideProgressDialog();
                    Log.w("AUTH", "User registered: " + user.getEmail());
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Log.w("AUTH", "SIGNED OUT");
                }
                hideProgressDialog();
            }
        };
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();

        RegisterFragment fragment = RegisterFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_register, fragment)
                .commit();

        registerPresenter = new RegisterPresenter();
        registerPresenter.initialize(this, fragment, auth, fbLogin);
        fragment.setPresenter(registerPresenter);
        fragment.setDialog(mProgressDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


}