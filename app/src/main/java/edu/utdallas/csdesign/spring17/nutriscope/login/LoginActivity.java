package edu.utdallas.csdesign.spring17.nutriscope.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import edu.utdallas.csdesign.spring17.nutriscope.overview.OverviewActivity;
import edu.utdallas.csdesign.spring17.nutriscope.R;


public class LoginActivity extends AppCompatActivity {


    private LoginPresenter loginPresenter;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.w("AUTH", "User signed in: " + user.getEmail());
                    hideProgressDialog();

                    FirebaseDatabase.getInstance().getReference().child("user").child(auth.getCurrentUser().getUid()).push();

                    startActivity(new Intent(LoginActivity.this, OverviewActivity.class));
                    finish();
                }
                else
                {
                    Log.w("AUTH", "SIGNED OUT");
                }
                hideProgressDialog();
            }
        };

        setContentView(R.layout.activity_login);

        LoginFragment fragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_login, fragment)
                .commit();

        loginPresenter = new LoginPresenter();
        loginPresenter.initialize(this,fragment,auth);
        fragment.setPresenter(loginPresenter);
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