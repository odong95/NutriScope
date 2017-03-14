package edu.utdallas.csdesign.spring17.nutriscope.login;


import com.facebook.AccessToken;

import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;

public interface LoginContract {
    interface View extends BaseView<Presenter> {

        void onErrorResponse(String error);

    }

    interface Presenter extends BasePresenter {

        void login(String email, String password);

        void handleFacebookAccessToken(AccessToken accessToken);

        void onErrorResponse(String error);

    }


}
