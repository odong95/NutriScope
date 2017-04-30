package edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/18/17.
 */
@Module
public class ConsumedFoodRepositoryModule {

    private final ConsumedFoodRepository consumedFoodRepository;

    public ConsumedFoodRepositoryModule(ConsumedFoodRepository consumedFoodRepository) {
        this.consumedFoodRepository = consumedFoodRepository;
    }

    @Provides
    ConsumedFoodRepository providesConsumedFoodRepository() {
        return consumedFoodRepository;
    }
}
