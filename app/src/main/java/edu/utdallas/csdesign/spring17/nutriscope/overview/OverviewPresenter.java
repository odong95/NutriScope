package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.support.annotation.NonNull;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by john on 2/10/17.
 */

public class OverviewPresenter implements OverviewContract.Presenter {

    private final Repository trackableRepository;
    private final OverviewContract.View overviewView;


    public OverviewPresenter(@NonNull Repository trackableRepository, @NonNull OverviewContract.View overviewView) {
        this.trackableRepository = checkNotNull(trackableRepository);
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
