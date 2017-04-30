package edu.utdallas.csdesign.spring17.nutriscope.login;

import android.util.Log;

import com.facebook.AccessToken;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.data.user.TaskStatus;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserListener;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManager;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserStatus;

public class LoginPresenter implements LoginContract.Presenter, UserListener {

    private static final String TAG = "LoginPresenter";

    private LoginContract.View view;
    private UserManager userManager;


    @Inject
    public LoginPresenter(LoginContract.View view, UserManager userManager) {
        this.view = view;
        this.userManager = userManager;
        userManager.addListener(this);
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void login(String email, String password) {
        userManager.loginUser(email, password, new TaskStatus() {
            @Override
            public void success(UserStatus msg) {
                // Should be taken care of by listener userLoggedIn;
            }

            @Override
            public void failure(UserStatus msg) {
                onErrorResponse("Login Failed");
            }
        });

    }

    @Override
    public void loginSuccessful() {
        Log.d(TAG, "loginSuccessful");
        if (view.isActive()) {
            view.loginSuccessful();
        }
    }

    @Override
    public void registerLoginFB(AccessToken accessToken) {
        userManager.registerUserFacebook(accessToken, new TaskStatus() {
            @Override
            public void success(UserStatus msg) {
                // Should be taken care of by listener userLoggedIn;
            }

            @Override
            public void failure(UserStatus msg) {
                onErrorResponse("Facebook Login Failed");

            }
        });
    }


    @Override
    public void onErrorResponse(String error) {
        view.onErrorResponse(error);
    }

    @Override
    public void userLoggedIn() {
        Log.d(TAG, "userLoggedIn() listener");
        loginSuccessful();
    }

    @Override
    public void userLoggedOut() {

    }
}
