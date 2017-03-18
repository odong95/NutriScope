package edu.utdallas.csdesign.spring17.nutriscope.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by john on 3/16/17.
 */

public class LoginInteractor implements LoginContract.Interactor {

    final private FirebaseAuth auth = FirebaseAuth.getInstance();

    private LoginContract.Presenter presenter;

    public LoginInteractor() {

    }

    public LoginInteractor(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    presenter.loginSuccessful();


                } else {
                    presenter.onErrorResponse("error");


                }
            }
        });


/*
        OnCo
                .addOnCompleteListener(a, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("AUTH", "signInWithEmail:failed", task.getException());
                            onErrorResponse("Login failed, please try again");
                        } else {
                            Log.d("AUTH", "signInWithEmail:success:" + auth.getCurrentUser().getEmail());
                        }
                    }
                });*/


    }


}
