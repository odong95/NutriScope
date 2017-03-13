package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

/**
 * The purpose of this class is to hold a sorted list of all items that will be displayed on the
 * over view.
 */

@Module
@Singleton
public class HistoryRepository implements Repository<Trackable>, Observer {

    private final static String TAG = "HistoryRepository";

    @Override
    public void update(Observable observable, Object arg) {
        Log.d(TAG, "update called");
        historyCache.add((Trackable) arg);

    }


    @Inject
    public HistoryRepository() {

    }

    List<Trackable> historyCache = new ArrayList<>();

    @Override
    public void createItem(Trackable item, CreateCallback callback) {

    }

    @Override
    public void updateItem(Trackable item, UpdateCallback callback) {

    }

    @Override
    public void queryItem(Specification specification, final QueryCallback<Trackable> callback) {

        // Get all consumed food

        callback.onQueryComplete(historyCache);


    }

    @Override
    public void deleteItem(Trackable id, DeleteCallback callback) {

    }


}
