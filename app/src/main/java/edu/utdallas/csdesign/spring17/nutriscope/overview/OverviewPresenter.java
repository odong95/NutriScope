package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by john on 2/10/17.
 */

final class OverviewPresenter implements OverviewContract.Presenter {

    final private static String TAG = "OverviewPresenter";

    private final OverviewContract.View overviewView;

    private final HistoryRepository historyRepository;

    @Inject
    public OverviewPresenter(@NonNull OverviewContract.View overviewView, HistoryRepository historyRepository) {
        this.overviewView = checkNotNull(overviewView);
        this.historyRepository = historyRepository;
    }

    @Inject
    void setupListeners() {
        overviewView.setPresenter(this);
    }


    @Override
    public void start() {

        List<Trackable> test = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            test.add(new ConsumedFood(i));

        }

        overviewView.showHistory(test);


        loadHistory();

    }

    @Override
    public void loadHistory() {
/*
        historyRepository.queryItem(null, new Repository.QueryCallback<Trackable>() {
            @Override
            public void onQueryComplete(List<Trackable> items) {
                Log.d(TAG, "history list loaded" + items.size());
                overviewView.showHistory(items);

            }

            @Override
            public void onDataNotAvailable() {


            }
        });*/


    }


}
