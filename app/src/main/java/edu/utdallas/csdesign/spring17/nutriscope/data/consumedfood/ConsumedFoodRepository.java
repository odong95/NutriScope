package edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import edu.utdallas.csdesign.spring17.nutriscope.ApplicationScope;
import edu.utdallas.csdesign.spring17.nutriscope.data.Repository;
import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;
import edu.utdallas.csdesign.spring17.nutriscope.data.history.HistoryRepository;

/**
 * Created by john on 3/10/17.
 */


@ApplicationScope
public class ConsumedFoodRepository extends Observable implements Repository<ConsumedFood> {


    ConsumedFoodFirebaseRepository consumedFoodFirebaseRepository;
    List<ConsumedFood> consumedFoodCache = new ArrayList<>();



    public ConsumedFoodRepository(HistoryRepository historyRepository, ConsumedFoodFirebaseRepository consumedFoodFirebaseRepository) {
        this.consumedFoodFirebaseRepository = consumedFoodFirebaseRepository;
        this.addObserver(historyRepository);
    }

    @Inject
    void setup() {
        this.consumedFoodFirebaseRepository = consumedFoodFirebaseRepository;


    }



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
