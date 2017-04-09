package edu.utdallas.csdesign.spring17.nutriscope.login;

import android.app.Activity;

import com.facebook.AccessToken;


import javax.inject.Inject;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private LoginContract.Interactor interactor;


    @Inject
    public LoginPresenter(LoginContract.Interactor interactor, LoginContract.View view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
        interactor.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void login(String email, String password) {
        interactor.login(email, password);
    }

    @Override
    public void loginSuccessful() {
        view.loginSuccessful();
    }

    public void loginfb(AccessToken accessToken, Activity a) {
        interactor.loginfb(accessToken, a);
    }


    @Override
    public void onErrorResponse(String error) {
        view.onErrorResponse(error);
    }


}
