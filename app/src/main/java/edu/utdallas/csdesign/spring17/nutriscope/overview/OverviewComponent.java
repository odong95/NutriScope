package edu.utdallas.csdesign.spring17.nutriscope.overview;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.FragmentScoped;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.nutrition.NutritionRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManagerComponent;

/**
 * Created by john on 3/18/17.
 */

@FragmentScoped
@Component(
        dependencies = {
                HistoryRepositoryComponent.class,
                NutritionRepositoryComponent.class,
                UserManagerComponent.class
        },
        modules = {
                OverviewModule.class
        })
public interface OverviewComponent {
    void inject(OverviewActivity activity);
}
