package edu.utdallas.csdesign.spring17.nutriscope.data.history;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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


    List<HistoryItem> history = new ArrayList<>();

    List<RepositoryInfo<Trackable>> repos = new LinkedList<>();

    public void putRepo(RepositoryInfo<Trackable> repo){
        repos.add(repo);
    }


    @Inject
    public HistoryRepository() {

    }

    @Override
    public void createItem(Trackable item, CreateCallback callback) {

    }

    @Override
    public void updateItem(Trackable item, UpdateCallback callback) {

    }

    @Override
    public void queryItem(Specification specification, final QueryCallback<Trackable> callback) {

        final List<Trackable> output = new ArrayList<>();

        for(int i = 0; i < repos.size(); i++) {

            final int index = i;
            repos.get(i).getRepo().queryItem(specification, new QueryCallback<Trackable>() {
                @Override
                public void onQueryComplete(List<Trackable> items) {
                    output.addAll(items);
                    if (index == repos.size() - 1) {
                        callback.onQueryComplete(output);
                        Log.d(TAG, "query called" + output.size());
                    }
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
