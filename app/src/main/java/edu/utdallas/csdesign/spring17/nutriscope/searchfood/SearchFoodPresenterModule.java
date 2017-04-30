package edu.utdallas.csdesign.spring17.nutriscope.searchfood;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/18/17.
 */

@Module
public class SearchFoodPresenterModule {

    private final SearchFoodContract.View view;

    public SearchFoodPresenterModule(SearchFoodContract.View view) {
        this.view = view;
    }

    @Provides
    SearchFoodContract.View provideSearchFoodContractView() {
        return view;
    }

}
