package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import android.support.annotation.Nullable;

import javax.inject.Named;

import dagger.Component;
import edu.utdallas.csdesign.spring17.nutriscope.FragmentScoped;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFoodRepositoryComponent;
import edu.utdallas.csdesign.spring17.nutriscope.data.food.FoodRepositoryComponent;

/**
 * Created by john on 3/18/17.
 */

@FragmentScoped
@Component(
        dependencies = {
                ConsumedFoodRepositoryComponent.class,
                FoodRepositoryComponent.class
        },
        modules = {
                AddEditFoodPresenterModule.class
        })
public interface AddEditFoodComponent {
    void inject(AddEditFoodActivity activity);


    @Named("ndbId")
    @Nullable
    String provideNdbId();

    @Named("foodName")
    @Nullable
    String provideFoodName();


}
