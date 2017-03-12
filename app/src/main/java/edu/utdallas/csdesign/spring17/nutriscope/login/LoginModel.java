package edu.utdallas.csdesign.spring17.nutriscope.login;





public class LoginModel implements LoginContract.Model{

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

    }




}


