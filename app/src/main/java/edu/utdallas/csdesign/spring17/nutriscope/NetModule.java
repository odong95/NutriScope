package edu.utdallas.csdesign.spring17.nutriscope;

import dagger.Module;
import dagger.Provides;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.AutoSuggestClient;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.AutoSuggestService;

/**
 * Created by john on 3/18/17.
 */

@Module
public class NetModule {

    @Provides
    AutoSuggestService provideAutoSuggestService() {
        return new AutoSuggestClient().getAutoSuggestService();
    }

}
