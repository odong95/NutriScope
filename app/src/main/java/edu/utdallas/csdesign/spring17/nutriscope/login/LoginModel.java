package edu.utdallas.csdesign.spring17.nutriscope.login;


import io.realm.ObjectServerError;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static edu.utdallas.csdesign.spring17.nutriscope.NutriscopeApplication.AUTH_URL;


public class LoginModel implements LoginContract.Model, SyncUser.Callback {

    public LoginContract.Presenter presenter;
    private LoginModelSecurity security;
    private boolean registering;

    public LoginModel(LoginContract.Presenter presenter) {
        this.presenter = presenter;
        security = new LoginModelSecurity(this);
    }

    @Override
    public void login(String username, String password) {
        //add security validation
        SyncUser.loginAsync(SyncCredentials.usernamePassword(username, password, false), AUTH_URL, this);
    }

    @Override
    public void register(String username, String password) {
        if (!security.isValidInput(username, password)) {
            presenter.errorInputResponse(true);
        } else {
            performRegister(username, password);
        }
    }

    private void performRegister(String username, String password) {
        //add security validation
        registering = true;
        SyncUser.loginAsync(SyncCredentials.usernamePassword(username, password, true), AUTH_URL, this);
    }


    @Override
    public void onSuccess(SyncUser user) {
        if (!registering) {
            presenter.onSuccess(user);
        } else {
            presenter.onRegisterResponse(true);
            registering = false;
        }
    }


    @Override
    public void onError(ObjectServerError error) {
        presenter.onError(error);
        registering = false;
    }

}


