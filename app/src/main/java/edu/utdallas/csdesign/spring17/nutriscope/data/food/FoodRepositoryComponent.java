package edu.utdallas.csdesign.spring17.nutriscope.data.food;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.ApplicationComponent;
import edu.utdallas.csdesign.spring17.nutriscope.ApplicationModule;

/**
 * Created by john on 3/18/17.
 */


@Component(modules = {FoodRepositoryModule.class, ApplicationModule.class})
public interface FoodRepositoryComponent extends ApplicationComponent {

    FoodRepository getFoodRepository();

}
