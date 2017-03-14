package edu.utdallas.csdesign.spring17.nutriscope.register;

import com.facebook.AccessToken;

import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;



public interface RegisterContract {

     interface View extends BaseView<RegisterContract.Presenter> {

        void onErrorResponse(String error);

    }

     interface Presenter extends BasePresenter {

        void register(String email, String password);

         void handleFacebookAccessToken(AccessToken accessToken);

        void onErrorResponse(String error);


    }

}
