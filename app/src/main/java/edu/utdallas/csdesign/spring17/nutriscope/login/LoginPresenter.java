package edu.utdallas.csdesign.spring17.nutriscope.login;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private Activity a;
    private FirebaseAuth auth;
    public LoginPresenter() {

    }

    public void initialize(Activity a, LoginContract.View view, FirebaseAuth auth)
    {
        this.a = a;
        this.view = view;
        this.auth = auth;
    }

    @Override
    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email,password)
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
                });
    }

    @Override
    public void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(a, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithCredential", task.getException());
                        }
                        else
                        {
                            Log.d("", "signInWithCredential:onComplete:" + task.isSuccessful());
                        }

                    }
                });
    }


    @Override
    public void onErrorResponse(String error) {
        view.onErrorResponse(error);
    }


    @Override
    public void start() {

    }


}
