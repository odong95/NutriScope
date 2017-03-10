package edu.utdallas.csdesign.spring17.nutriscope;

import javax.inject.Singleton;


import edu.utdallas.csdesign.spring17.nutriscope.addeditfood.AddEditFoodActivity;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ConsumedFoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.FoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.AutoSuggestClient;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.FoodReportClient;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.FoodReportService;
import edu.utdallas.csdesign.spring17.nutriscope.searchfood.SearchFoodActivity;

/**
 * Created by john on 3/10/17.
 */

@Singleton
@dagger.Component(modules={AutoSuggestClient.class, FoodReportClient.class, ConsumedFoodRepository.class, FoodRepository.class})
public interface Component {
    void inject(SearchFoodActivity searchFoodActivity);
    void inject(AddEditFoodActivity addEditFoodActivity);

    FoodReportService foodReportService();
    FoodRepository foodRepository();


}
