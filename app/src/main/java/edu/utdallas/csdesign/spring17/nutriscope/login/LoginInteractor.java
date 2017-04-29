package edu.utdallas.csdesign.spring17.nutriscope.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.utdallas.csdesign.spring17.nutriscope.data.user.User;


public class LoginInteractor implements LoginContract.Interactor {

    final private FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");
    private LoginContract.Presenter presenter;

    public LoginInteractor() {

    }

    @Override
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
                    presenter.onErrorResponse("Login failed, please try again");
                }
            }
        });

    }

    @Override
    public void loginfb(AccessToken accessToken, Activity a) {
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
                            setupUserInfo(new User(auth.getCurrentUser().getUid(), auth.getCurrentUser().getEmail()));
                        }

                    }
                });
    }

    private void setupUserInfo(User user) {
        db.child(user.getUid()).setValue(user);
        presenter.loginSuccessful();
    }

}
