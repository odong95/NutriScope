package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.HistoryRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.Repository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by john on 2/10/17.
 */

public class OverviewPresenter implements OverviewContract.Presenter {

    final private static String TAG = "OverviewPresenter";

    private final OverviewContract.View overviewView;

    private final HistoryRepository historyRepository;

    public OverviewPresenter(@NonNull OverviewContract.View overviewView, HistoryRepository historyRepository) {
        this.overviewView = checkNotNull(overviewView);
        this.historyRepository = historyRepository;
        overviewView.setPresenter(this);
    }

    @Override
    public void start() {
        loadHistory();

    }

    @Override
    public void loadHistory() {
        historyRepository.queryItem(null, new Repository.QueryCallback<Trackable>() {
            @Override
            public void onQueryComplete(List<Trackable> items) {
                Log.d(TAG, "history list loaded" + items.size());
                overviewView.showHistory(items);

            }

            @Override
            public void onDataNotAvailable() {


            }
        });


    }
}
