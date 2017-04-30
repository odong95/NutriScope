package edu.utdallas.csdesign.spring17.nutriscope.settings;

import javax.inject.Inject;

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
       // userManager.addListener(this);
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
