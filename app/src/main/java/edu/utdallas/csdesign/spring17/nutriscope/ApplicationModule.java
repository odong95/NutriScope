package edu.utdallas.csdesign.spring17.nutriscope;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/10/17.
 */

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    Application providesApplication() {
        return application;
    }


/*    @Provides
    @Local
    FoodReportService provideFoodReportService() {
        return new FoodReportClient().getFoodReportService();
    }*/


}

