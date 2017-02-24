package edu.utdallas.csdesign.spring17.nutriscope.login;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;


public class LoginModel implements LoginContract.Model {

    public LoginContract.Presenter presenter;
    private LoginModelSecurity security;

    public LoginModel(LoginContract.Presenter presenter, MobileServiceClient client) {
        this.presenter = presenter;
        security = new LoginModelSecurity(client, this);
    }

    @Override
    public void login(String username, String password) {
        security.attemptLogin(username, password);
    }

    @Override
    public void register(String username, String password) {
        if (!security.isValidInput(username, password)) {
            presenter.onRegisterResponse(false);
        } else {
            performRegister(username, password);
        }
    }

    private void performRegister(String username, String password) {
        if (security.performRegister(username, password)) {
            presenter.onRegisterResponse(true);
        }
    }


}


