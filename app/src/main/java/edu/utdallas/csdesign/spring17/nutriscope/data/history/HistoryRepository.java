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
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserListener;
import edu.utdallas.csdesign.spring17.nutriscope.data.user.UserManager;

/**
 * The purpose of this class is to hold a sorted list of all items that will be displayed on the
 * over view.
 */


@ApplicationScope
public class HistoryRepository implements Repository<HistoryItem>, UserListener {
    private final static String TAG = "HistoryRepository";

    private boolean isInit = false;
    private boolean userSwitched = true;

    List<HistoryItem> history = new ArrayList<>();

    List<RepositoryInfo<Trackable>> repos = new LinkedList<>();

    public void putRepo(RepositoryInfo<Trackable> repo) {
        Log.d(TAG, repo.getType().toString());
        repos.add(repo);
    }

    UserManager userManager;

    @Inject
    public HistoryRepository(UserManager userManager) {
        this.userManager = userManager;
        userManager.addListener(this);


    }

    @Override
    public void userLoggedIn() {
        Log.d(TAG, "user logged in, clearing history");
        isInit = false;


    }

    @Override
    public void userLoggedOut() {
        Log.d(TAG, "user logged out, clearing history");
        userSwitched = true;
        isInit = false;

    }

    @Override
    public void createItem(HistoryItem item, CreateCallback callback) {
        history.add(item);
        callback.onCreateComplete();
    }

    @Override
    public void updateItem(HistoryItem item, UpdateCallback callback) {
        for (int i = 0; i < history.size(); i++) {
            if (item.getKey().equals(history.get(i).getKey())) {
                history.set(i, item);
                callback.onUpdateComplete();
                return;
            }
        }
        callback.onUpdateFailed();
    }

    @Override
    public void queryItem(Specification specification, final QueryCallback<HistoryItem> callback) {


        // return entire sorted list if spec is null
        if (specification == null) {
            Log.d(TAG, "history query called");
            // the first time, go to each child repo and query full contents
            if (!isInit) {
                Log.d(TAG, "first time");
                history.clear();
                for (int i = 0; i < repos.size(); i++) {
                    Log.d(TAG, "for each repo");

                    final int index = i;
                    repos.get(i).getRepo().queryItem(specification, new QueryCallback<Trackable>() {
                        @Override
                        public void onQueryComplete(List<Trackable> items) {
                            Log.d(TAG, "retrieved repo's items");
                            for (Trackable item : items) {
                                history.add(new HistoryItem(repos.get(index).getType(), item.getKey(), item));
                            }
                            if (index == repos.size() - 1) {
                                isInit = true;
                                callback.onQueryComplete(history);
                                Log.d(TAG, "query called" + history.size());
                            }
                        }

                        @Override
                        public void onDataNotAvailable() {
                            callback.onDataNotAvailable();

                        }
                    });

                }
            }
            // else just return current state
            else {
                Log.d(TAG, "return current state " + history.size());
                callback.onQueryComplete(history);
            }
        }

    }


    @Override
    public void deleteItem(HistoryItem item, DeleteCallback callback) {
        for (int i = 0; i < history.size(); i++) {
            if (item.getKey().equals(history.get(i).getKey())) {
                history.remove(i);
                callback.onDeleteComplete();
                return;
            }
        }
        callback.onDeleteFailed();
    }


}
