package edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood;

import dagger.Module;
import dagger.Provides;
import edu.utdallas.csdesign.spring17.nutriscope.ApplicationScope;
import edu.utdallas.csdesign.spring17.nutriscope.data.Remote;

/**
 * Created by john on 3/18/17.
 */
@Module
public class ConsumedFoodRepositoryModule {

    @ApplicationScope
    @Provides
    @Remote
    ConsumedFoodFirebaseRepository provideConsumedFoodFirebaseRepository() {
        return new ConsumedFoodFirebaseRepository();
    }


}
