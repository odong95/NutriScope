package edu.utdallas.csdesign.spring17.nutriscope.register;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/18/17.
 */

@Module
public class RegisterPresenterModule {

    private RegisterContract.View view;

    public RegisterPresenterModule(RegisterContract.View view) {
        this.view = view;
    }

    @Provides
    RegisterContract.View provideRegisterContractView() {
        return view;
    }
}
