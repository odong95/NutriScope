package edu.utdallas.csdesign.spring17.nutriscope.login;


import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginModel model;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view, MobileServiceClient client) {
        this.view = view;
        this.model = new LoginModel(this, client);
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
    public void onLoginResponse(boolean isLoggedIn) {
        view.onLoginResponse(isLoggedIn);
    }

    @Override
    public void onRegisterResponse(boolean isRegistered) {
        view.onRegisterResponse(isRegistered);
    }
}
