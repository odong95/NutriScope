package edu.utdallas.csdesign.spring17.nutriscope.searchfood;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.FragmentScoped;
import edu.utdallas.csdesign.spring17.nutriscope.NetComponent;
import edu.utdallas.csdesign.spring17.nutriscope.NetModule;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.FoodRepositoryComponent;

/**
 * Created by john on 3/18/17.
 */

@FragmentScoped
@Component(
        dependencies = {
                FoodRepositoryComponent.class,
                NetComponent.class
        },
        modules = {
                SearchFoodPresenterModule.class,
                NetModule.class
        })
public interface SearchFoodComponent {
    void inject(SearchFoodActivity activity);
}
