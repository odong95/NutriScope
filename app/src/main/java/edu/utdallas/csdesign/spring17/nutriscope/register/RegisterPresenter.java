package edu.utdallas.csdesign.spring17.nutriscope.register;

import javax.inject.Inject;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private RegisterContract.Interactor interactor;

    @Inject
    public RegisterPresenter(RegisterContract.View view, RegisterContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
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
    public void register(String email, String password) {
        interactor.register(email, password);
    }

    @Override
    public void onRegisterComplete() {
        view.onRegisterComplete();
    }


    @Override
    public void onErrorResponse(String error) {
        view.onErrorResponse(error);
    }

}
