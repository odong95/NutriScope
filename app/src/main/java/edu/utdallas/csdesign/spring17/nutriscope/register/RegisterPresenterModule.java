package edu.utdallas.csdesign.spring17.nutriscope.register;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/18/17.
 */

@Module
public class RegisterPresenterModule {

    private RegisterContract.View view;
    private RegisterContract.Interactor interactor;

    public RegisterPresenterModule(RegisterContract.View view, RegisterContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Provides
    RegisterContract.View provideRegisterContractView() {
        return view;
    }

    @Provides
    RegisterContract.Interactor provideRegisterContractInteractor() {
        return interactor;
    }


}
