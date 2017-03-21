package edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.ApplicationScope;
import edu.utdallas.csdesign.spring17.nutriscope.data.Castable;
import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepository;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.RepositoryInfo;

/**
 * Created by john on 3/10/17.
 */


@ApplicationScope
public class ConsumedFoodRepository implements Castable, Repository<ConsumedFood> {
    public static final Class type = ConsumedFood.class;

    @Override
    public Class getType() {
        return type;
    }

    public static final String TAG = "CFrepo";

    private ConsumedFoodFirebaseRepository consumedFoodFirebaseRepository;
    private HistoryRepository historyRepository;






    public ConsumedFoodRepository(HistoryRepository historyRepository, ConsumedFoodFirebaseRepository consumedFoodFirebaseRepository) {
        this.consumedFoodFirebaseRepository = consumedFoodFirebaseRepository;
        this.historyRepository = historyRepository;
        historyRepository.putRepo(new RepositoryInfo<>(ConsumedFood.class, this));


    }

    List<ConsumedFood> consumedFoodCache = new ArrayList<>();

    @Override
    public void createItem(ConsumedFood item, CreateCallback callback) {
        consumedFoodFirebaseRepository.createItem(item, callback);
        consumedFoodCache.add(item);

    }

    @Override
    public void updateItem(ConsumedFood item, UpdateCallback callback) {

    }

    @Override
    public void queryItem(Specification specification, final QueryCallback<ConsumedFood> callback) {
        Log.d(TAG, "query initiated");
        consumedFoodFirebaseRepository.queryItem(null, new QueryCallback<ConsumedFood>() {
            @Override
            public void onQueryComplete(List<ConsumedFood> items) {
                Log.d(TAG, "Food returned " + items.size() );
                consumedFoodCache.addAll(items);
                callback.onQueryComplete(items);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });


    }

    @Override
    public void deleteItem(ConsumedFood id, DeleteCallback callback) {

    }

}
