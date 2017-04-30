package edu.utdallas.csdesign.spring17.nutriscope.register;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.FragmentScoped;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManagerComponent;

/**
 * Created by john on 3/18/17.
 */

@FragmentScoped
@Component(
        dependencies = {
                UserManagerComponent.class

        },
        modules = {
                RegisterPresenterModule.class
        }
)
public interface RegisterComponent {
    void inject(RegisterActivity activity);

}
