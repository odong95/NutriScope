package edu.utdallas.csdesign.spring17.nutriscope.data.history;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.ApplicationScope;
import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;
import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

/**
 * The purpose of this class is to hold a sorted list of all items that will be displayed on the
 * over view.
 */


@ApplicationScope
public class HistoryRepository implements Repository<Trackable>, Observer {

    private final static String TAG = "HistoryRepository";
    List<Trackable> historyCache = new ArrayList<>();


    @Inject
    public HistoryRepository() {

    }

    @Override
    public void update(Observable observable, Object arg) {

        historyCache.add((Trackable) arg);
        Log.d(TAG, "update called " + historyCache.size());

    }

    @Override
    public void createItem(Trackable item, CreateCallback callback) {

    }

    @Override
    public void updateItem(Trackable item, UpdateCallback callback) {

    }

    @Override
    public void queryItem(Specification specification, final QueryCallback<Trackable> callback) {

        // Get all consumed food
        Log.d(TAG, "query called" + historyCache.size());
        callback.onQueryComplete(historyCache);


    }

    @Override
    public void deleteItem(Trackable id, DeleteCallback callback) {

    }


}
