package edu.utdallas.csdesign.spring17.nutriscope.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/18/17.
 */

@Module
public class LoginPresenterModule {

    private final LoginContract.View view;
    private final LoginContract.Interactor interactor;

    public LoginPresenterModule(LoginContract.View view, LoginContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Provides
    LoginContract.View provideLoginContractView() {
        return view;
    }

    @Provides
    LoginContract.Interactor provideLoginContractInteractor() {
        return interactor;
    }

}
