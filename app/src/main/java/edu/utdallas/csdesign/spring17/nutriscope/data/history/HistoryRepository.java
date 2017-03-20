package edu.utdallas.csdesign.spring17.nutriscope.data.history;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class HistoryRepository implements Repository<Trackable> {

    private final static String TAG = "HistoryRepository";

    public List<HistoryTrackable> getHistoryCache() {
        return historyCache;
    }

    List<HistoryTrackable> historyCache = new ArrayList<>();

    // Takes the class of the item being collected.
    Map<Class<?>, Repository<Trackable>> children = new HashMap<>();


    @Inject
    public HistoryRepository() {

    }

    public void addObservable(Class<?> repoId, Repository<? extends Trackable> repo) {
        children.put(repoId, (Repository<Trackable>)repo);
    }


/*    @Override
    public void update(Observable observable, Object arg) {
        if (arg instanceof Repository) {
            children.add((Repository) arg);
            Log.d(TAG, "repository added");
        }
        children.add((Repository)observable);


        Log.d(TAG, "update called " + children.size());
        queryItem(null, new QueryCallback<Trackable>() {
            @Override
            public void onQueryComplete(List<Trackable> items) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }*/

    @Override
    public void createItem(Trackable item, CreateCallback callback) {

    }

    @Override
    public void updateItem(Trackable item, UpdateCallback callback) {

    }

    @Override
    public void queryItem(Specification specification, final QueryCallback<Trackable> callback) {
        Log.d(TAG, "query initiated" + children.size());

        final List<Trackable> output = new LinkedList<>();

        for(final Map.Entry<Class<?>, Repository<Trackable>> repo: children.entrySet()) {
            Log.d(TAG, repo.getKey().getName());

            repo.getValue().queryItem(specification, new QueryCallback<Trackable>() {
                @Override
                public void onQueryComplete(List<Trackable> items) {
                    Log.d(TAG, "on query complete " +  items.size());

                    callback.onQueryComplete(items);/*
                    for(int i = 0; i < items.size(); i++) {
                        getHistoryCache().add(new HistoryTrackable(repo.getKey(), items.get(i).getItemId()));
                        output.add(items.get(i));
                        if (i == items.size() - 1) {
                            Collections.sort(getHistoryCache());
                            callback.onQueryComplete(output);
                            Log.d(TAG, "query called" + output.size());
                        }
                    }
*/
                }

                @Override
                public void onDataNotAvailable() {

                }
            });

        }

    }




    @Override
    public void deleteItem(Trackable id, DeleteCallback callback) {

    }


}
