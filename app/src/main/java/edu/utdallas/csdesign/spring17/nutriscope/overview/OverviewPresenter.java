package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by john on 2/10/17.
 */

public class OverviewPresenter implements OverviewContract.Presenter {


    private final OverviewContract.View overviewView;


    public OverviewPresenter(@NonNull OverviewContract.View overviewView) {
        this.overviewView = checkNotNull(overviewView);
        overviewView.setPresenter(this);
    }

    @Override
    public void start() {
        loadHistory();

    }

    @Override
    public void loadHistory() {


    }
}
