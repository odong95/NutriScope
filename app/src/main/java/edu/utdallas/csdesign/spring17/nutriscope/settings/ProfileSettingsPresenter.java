package edu.utdallas.csdesign.spring17.nutriscope.settings;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.data.user.User;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManager;

/**
 * Created by john on 4/29/17.
 */

public class ProfileSettingsPresenter implements ProfileSettingsContract.Presenter {


    ProfileSettingsContract.View view;
    private UserManager userManager;

    @Inject
    public ProfileSettingsPresenter(ProfileSettingsContract.View view, UserManager userManager) {
        this.view = view;
        this.userManager = userManager;
    }

   @Inject
    void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {
        getUser1();
    }


    private void getUser1() {
        userManager.getUser(new UserManager.GetUser() {
            @Override
            public void getUser(User user) {
                view.populateUser(user);
            }
        });
    }

    @Override
    public void getUser(final ProfileSettingsContract.RetrieveUser retrieveUser) {
        userManager.getUser(new UserManager.GetUser() {
            @Override
            public void getUser(User user) {
                retrieveUser.getUser(user);
                view.populateUser(user);
            }
        });
    }

    @Override
    public void modifyUser(User user) {
        userManager.setUser(user);
        view.populateUser(user);

    }



}
