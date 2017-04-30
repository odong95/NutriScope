package edu.utdallas.csdesign.spring17.nutriscope;

/**
 * Created by john on 3/10/17.
 */

@ApplicationScope
@dagger.Component(


        modules = {
                ApplicationModule.class,
        })
public interface ApplicationComponent {

    // Each subcomponent can depend on more than one module

}
