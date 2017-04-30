package edu.utdallas.csdesign.spring17.nutriscope.overview;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/18/17.
 */

@Module
public class OverviewModule {

    private final OverviewContract.View view;

    public OverviewModule(OverviewContract.View view) {
        this.view = view;
    }

    @Provides
    OverviewContract.View provideOverviewContractView() {
        return view;
    }

}
