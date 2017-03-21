package edu.utdallas.csdesign.spring17.nutriscope.register;

import edu.utdallas.csdesign.spring17.nutriscope.BaseInteractor;
import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;


public interface RegisterContract {

    interface View extends BaseView<RegisterContract.Presenter> {

        void onErrorResponse(String error);

        void onRegisterComplete();


    }

    interface Presenter extends BasePresenter {

        void register(String email, String password);

        void onErrorResponse(String error);

        void onRegisterComplete();


    }

    interface Interactor extends BaseInteractor<Presenter> {

        void register(String email, String password);



    }


}
