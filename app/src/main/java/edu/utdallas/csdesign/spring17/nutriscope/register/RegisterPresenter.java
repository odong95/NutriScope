package edu.utdallas.csdesign.spring17.nutriscope.register;

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

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private FirebaseAuth auth;
    private Activity a;
    private boolean fbLogin;

    public RegisterPresenter() {

    }

    public void initialize(Activity a, RegisterContract.View view, FirebaseAuth auth, boolean fb) {
        this.a = a;
        this.view = view;
        this.auth = auth;
        this.fbLogin = fb;
    }

    @Override
    public void register(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(a, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("AUTH", "createUserWithEmail:failed", task.getException());
                            onErrorResponse("Registration failed, please try again");
                        } else {
                            Log.d("AUTH", "createUserWithEmail:success:" + auth.getCurrentUser().getEmail());
                            onErrorResponse("Registration Successful");
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
                        } else {
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
