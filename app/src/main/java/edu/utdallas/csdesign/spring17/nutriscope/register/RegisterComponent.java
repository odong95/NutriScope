package edu.utdallas.csdesign.spring17.nutriscope.register;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.FragmentScoped;

/**
 * Created by john on 3/18/17.
 */

@FragmentScoped
@Component(
        dependencies = {

        },
        modules = {
                RegisterPresenterModule.class
        }
)
public interface RegisterComponent {
    void inject(RegisterActivity activity);

}
