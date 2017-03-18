package edu.utdallas.csdesign.spring17.nutriscope.overview;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.FragmentScoped;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepositoryComponent;

/**
 * Created by john on 3/18/17.
 */

@FragmentScoped
@Component(
        dependencies = {
                HistoryRepositoryComponent.class
        },
        modules = {
                OverviewPresenterModule.class
        })
public interface OverviewComponent {
    void inject(OverviewActivity activity);
}
