package edu.utdallas.csdesign.spring17.nutriscope.data.history;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.ApplicationComponent;

/**
 * Created by john on 3/18/17.
 */


@Component(modules = {HistoryRepositoryModule.class})
public interface HistoryRepositoryComponent extends ApplicationComponent {

    HistoryRepository getHistoryRepository();

}
