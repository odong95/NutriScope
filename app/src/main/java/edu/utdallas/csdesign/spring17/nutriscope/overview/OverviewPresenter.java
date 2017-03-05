package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.support.annotation.NonNull;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.TrackableDataSource;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.TrackableRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by john on 2/10/17.
 */

public class OverviewPresenter implements OverviewContract.Presenter {

    private final TrackableRepository trackableRepository;
    private final OverviewContract.View overviewView;


    public OverviewPresenter(@NonNull TrackableRepository trackableRepository, @NonNull OverviewContract.View overviewView) {
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
        trackableRepository.getHistory(new TrackableDataSource.LoadHistoryCallback() {
            @Override
            public void onHistoryLoaded(List<Trackable> history) {
                overviewView.showHistory(history);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }
}
