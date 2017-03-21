package edu.utdallas.csdesign.spring17.nutriscope.register;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.firebase.User;

/**
 * Created by john on 3/18/17.
 */

public class RegisterInteractor implements RegisterContract.Interactor {

    RegisterContract.Presenter presenter;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");

    public RegisterInteractor() {

    }

    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("AUTH", "createUserWithEmail:failed", task.getException());
                            presenter.onErrorResponse("Registration failed, please try again");
                        } else {
                            Log.d("AUTH", "createUserWithEmail:success:" + auth.getCurrentUser().getEmail());
                            setupUserInfo(new User(auth.getCurrentUser().getUid(), auth.getCurrentUser().getEmail()));
                        }
                    }
                });
    }

    private void setupUserInfo(User user) {
        db.child(user.getUid()).setValue(user);
        presenter.onRegisterComplete();

    }


}
