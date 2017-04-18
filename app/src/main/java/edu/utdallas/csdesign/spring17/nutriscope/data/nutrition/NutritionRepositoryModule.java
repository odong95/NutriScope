package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 4/17/17.
 */

@Module
public class NutritionRepositoryModule {

    private final NutritionRepository nutritionRepository;

    public NutritionRepositoryModule(NutritionRepository nutritionRepository) {
        this.nutritionRepository = nutritionRepository;
    }

    @Provides
    NutritionRepository providesNutritionRepository() {
        return nutritionRepository;
    }

}
