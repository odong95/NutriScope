package edu.utdallas.csdesign.spring17.nutriscope;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.ConsumedFoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.FoodRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.AutoSuggestClient;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.FoodReportClient;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.FoodReportService;

/**
 * Created by john on 3/10/17.
 */

public enum Injector {
    INSTANCE;

    Component component;

    private Injector(){
    }

    static void initialize(NutriscopeApplication nutriscopeApplication) {
        Component component = DaggerComponent.builder()
                .autoSuggestClient(new AutoSuggestClient())
                .foodReportClient(new FoodReportClient())
                .build();

        FoodReportService service = component.foodReportService();

        INSTANCE.component = component;
    }

    public static Component get() {
        return INSTANCE.component;
    }

}
