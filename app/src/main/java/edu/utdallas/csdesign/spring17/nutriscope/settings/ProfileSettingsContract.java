package edu.utdallas.csdesign.spring17.nutriscope.settings;


import edu.utdallas.csdesign.spring17.nutriscope.BasePresenter;
import edu.utdallas.csdesign.spring17.nutriscope.BaseView;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.User;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManager;

/**
 * Created by john on 4/29/17.
 */

public interface ProfileSettingsContract {

    interface View extends BaseView<Presenter> {

        void populateUser(User user);


    }

    interface Presenter extends BasePresenter {

        void getUser(RetrieveUser retrieveUser);
        void modifyUser(User user);


    }

    interface RetrieveUser {
        void getUser(User user);
    }

}
