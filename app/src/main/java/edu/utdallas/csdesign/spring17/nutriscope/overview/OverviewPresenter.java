package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.support.annotation.NonNull;

import edu.utdallas.csdesign.spring17.nutriscope.data.NutritionTracker;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by john on 2/10/17.
 */

public class OverviewPresenter implements OverviewContract.Presenter {

    public OverviewPresenter(@NonNull NutritionTracker nutritionTracker, @NonNull OverviewContract.View overviewView) {
        nutritionTracker = checkNotNull(nutritionTracker);
        overviewView = checkNotNull(overviewView);
        overviewView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadDailySummaries() {

    }
}
