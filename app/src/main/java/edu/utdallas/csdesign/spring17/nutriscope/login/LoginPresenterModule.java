package edu.utdallas.csdesign.spring17.nutriscope.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/18/17.
 */

@Module
public class LoginPresenterModule {

    private final LoginContract.View view;

    public LoginPresenterModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    LoginContract.View provideLoginContractView() {
        return view;
    }

}
