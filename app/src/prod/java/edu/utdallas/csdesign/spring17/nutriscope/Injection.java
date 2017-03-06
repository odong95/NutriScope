package edu.utdallas.csdesign.spring17.nutriscope;

import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.AutoSuggestClient;
import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.AutoSuggestService;
import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.FoodReportClient;
import edu.utdallas.csdesign.spring17.nutriscope.data.ndb.FoodReportService;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.FoodRealmRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;

/**
 * Created by john on 2/10/17.
 */


public class Injection {

    public static FoodReportService provideFoodReportService() {

        return FoodReportClient.getInstance().getFoodReportService();
    }

    public static AutoSuggestService provideAutoSuggestService() {

        return AutoSuggestClient.getInstance().getAutoSuggestService();
    }

    public static Repository provideRepository() {
        return FoodRealmRepository.getInstance();
    }
}



