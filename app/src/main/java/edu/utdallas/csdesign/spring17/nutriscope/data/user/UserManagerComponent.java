package edu.utdallas.csdesign.spring17.nutriscope.data.user;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.ApplicationModule;
import edu.utdallas.csdesign.spring17.nutriscope.ApplicationScope;

/**
 * Created by john on 4/29/17.
 */

@ApplicationScope
@Component(modules = {UserManagerModule.class, ApplicationModule.class})
public interface UserManagerComponent {
    UserManager getUserManager();
}
