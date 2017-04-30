package edu.utdallas.csdesign.spring17.nutriscope.data.user;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 4/29/17.
 */

@Module
public class UserManagerModule {

    private final UserManager userManager;

    public UserManagerModule(UserManager userManager) {
        this.userManager = userManager;
    }

    @Provides
    UserManager providesUserManager() {
        return userManager;
    }


}
