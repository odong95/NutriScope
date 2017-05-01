package edu.utdallas.csdesign.spring17.nutriscope.settings;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.FragmentScoped;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManagerComponent;

/**
 * Created by john on 4/29/17.
 */

@FragmentScoped
@Component(
        dependencies = {
                UserManagerComponent.class
        },
        modules = {
                ProfileSettingsModule.class
})
public interface ProfileSettingsComponent {
    void inject(ProfileSettingsActivity activity);
}
