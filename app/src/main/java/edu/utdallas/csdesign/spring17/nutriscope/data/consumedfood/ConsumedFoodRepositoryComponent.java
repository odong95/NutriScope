package edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.ApplicationModule;
import edu.utdallas.csdesign.spring17.nutriscope.ApplicationScope;

/**
 * Created by john on 3/18/17.
 */

@ApplicationScope
@Component(modules = {ConsumedFoodRepositoryModule.class, ApplicationModule.class})
public interface ConsumedFoodRepositoryComponent {

    ConsumedFoodRepository getConsumedFoodRepository();

}