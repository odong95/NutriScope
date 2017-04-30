package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import android.support.annotation.Nullable;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/18/17.
 */

@Module
public class AddEditFoodPresenterModule {

    private final AddEditFoodContract.View view;
    private String ndbId;
    private String foodName;
    private boolean isConsumedFood;

    public AddEditFoodPresenterModule(AddEditFoodContract.View view, @Nullable String ndbId, @Nullable String foodName, boolean isConsumedFood) {
        this.view = view;
        this.ndbId = ndbId;
        this.foodName = foodName;
        this.isConsumedFood = isConsumedFood;
    }

    @Provides
    AddEditFoodContract.View providesAddEditFoodContractView() {
        return view;
    }

    @Provides
    @Named("ndbId")
    @Nullable
    String provideNdbId() {
        return ndbId;
    }

    @Provides
    @Named("foodName")
    @Nullable
    String provideFoodName() {
        return foodName;
    }

    @Provides
    boolean provideIsConsumedFood() {
        return isConsumedFood;
    }

}
