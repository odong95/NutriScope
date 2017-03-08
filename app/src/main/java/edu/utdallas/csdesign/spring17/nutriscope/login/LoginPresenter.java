package edu.utdallas.csdesign.spring17.nutriscope.login;

import io.realm.ObjectServerError;
import io.realm.SyncUser;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginModel model;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.model = new LoginModel(this);
    }

    @Override
    public void login(String username, String password) {
        model.login(username, password);
    }

    @Override
    public void register(String username, String password) {
        model.register(username, password);
    }


    @Override
    public void onSuccess(SyncUser user) {
        view.onSuccess(user);
    }

    @Override
    public void onError(ObjectServerError error) {
        view.onError(error);
    }

    @Override
    public void errorInputResponse(Boolean isValid) {
        view.errorInputResponse(isValid);
    }

    @Override
    public void onRegisterResponse(Boolean isRegistered) {
        view.onRegisterResponse(isRegistered);
    }
}
