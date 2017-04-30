package edu.utdallas.csdesign.spring17.nutriscope.login;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.FragmentScoped;

/**
 * Created by john on 3/18/17.
 */

@FragmentScoped
@Component(
        dependencies = {},
        modules = {
                LoginPresenterModule.class
        })
public interface LoginComponent {
    void inject(LoginActivity activity);
}
