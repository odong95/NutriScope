package edu.utdallas.csdesign.spring17.nutriscope.settings;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 4/29/17.
 */

@Module
public class ProfileSettingsModule {

    private final ProfileSettingsContract.View view;

    public ProfileSettingsModule(ProfileSettingsContract.View view) {
        this.view = view;
    }

    @Provides
    ProfileSettingsContract.View provideProfileSettingsContractView() {
        return view;
    }
}
