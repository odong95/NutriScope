package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.utdallas.csdesign.spring17.nutriscope.data.ConsumedFood;

/**
 * Created by john on 3/10/17.
 */

@Module
@Singleton
public class ConsumedFoodRepository extends Observable implements Repository<ConsumedFood> {


    ConsumedFoodFirebaseRepository consumedFoodFirebaseRepository;

    @Inject
    public ConsumedFoodRepository(ConsumedFoodFirebaseRepository consumedFoodFirebaseRepository, HistoryRepository historyRepository) {
        this.consumedFoodFirebaseRepository = consumedFoodFirebaseRepository;
        this.addObserver(historyRepository);

    }



    List<ConsumedFood> consumedFoodCache = new ArrayList<>();


    @Override
    public void createItem(ConsumedFood item, CreateCallback callback) {
        consumedFoodFirebaseRepository.createItem(item, callback);

        consumedFoodCache.add(item);
        setChanged();
        notifyObservers(item);
        callback.onCreateComplete();

    }

    @Override
    public void updateItem(ConsumedFood item, UpdateCallback callback) {

    }

    @Override
    public void queryItem(Specification specification, QueryCallback callback) {

    }

    @Override
    public void deleteItem(ConsumedFood id, DeleteCallback callback) {

    }

}
