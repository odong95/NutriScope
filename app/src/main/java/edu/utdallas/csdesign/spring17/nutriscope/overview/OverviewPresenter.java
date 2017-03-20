package edu.utdallas.csdesign.spring17.nutriscope.overview;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by john on 2/10/17.
 */

final class OverviewPresenter implements OverviewContract.Presenter {

    final private static String TAG = "OverviewPresenter";

    private final OverviewContract.View view;

    private final HistoryRepository historyRepository;

    @Inject
    public OverviewPresenter(@NonNull OverviewContract.View view, HistoryRepository historyRepository) {
        this.view = checkNotNull(view);
        this.historyRepository = historyRepository;
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }


    @Override
    public void start() {
        Log.d(TAG, "start");
        loadHistory();

    }

    @Override
    public void loadHistory() {

        historyRepository.queryItem(null, new Repository.QueryCallback<Trackable>() {
            @Override
            public void onQueryComplete(List<Trackable> items) {
                Log.d(TAG, "history list loaded " + items.size());
                view.showHistory(items);

            }

            @Override
            public void onDataNotAvailable() {


            }
        });


    }

    @Override
    public void openAddEditFood(ConsumedFood consumedFood) {
        view.showAddEditFood(consumedFood.getNdbNo());

    }


}
