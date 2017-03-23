package edu.utdallas.csdesign.spring17.nutriscope.login;


import edu.utdallas.csdesign.spring17.nutriscope.BaseInteractor;
import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;

public interface LoginContract {
    interface View extends BaseView<Presenter> {

        void onErrorResponse(String error);

        void loginSuccessful();

    }

    interface Presenter extends BasePresenter {

        void login(String email, String password);

        void loginSuccessful();

        void onErrorResponse(String error);

    }

    interface Interactor extends BaseInteractor<Presenter> {

        void login(String email, String password);


        //void createUser();


    }


}
