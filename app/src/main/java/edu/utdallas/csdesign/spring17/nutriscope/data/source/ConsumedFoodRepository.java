package edu.utdallas.csdesign.spring17.nutriscope.data.source;

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
public class ConsumedFoodRepository implements Repository<ConsumedFood> {


    ConsumedFoodFirebaseRepository consumedFoodFirebaseRepository;

    ConsumedFoodRepository consumedFoodRepository;

    @Inject
    public ConsumedFoodRepository(ConsumedFoodFirebaseRepository consumedFoodFirebaseRepository) {
        this.consumedFoodFirebaseRepository = consumedFoodFirebaseRepository;

    }

    /*
    @Provides
    @Singleton
    public ConsumedFoodRepository getConsumedFoodRepository() {
        consumedFoodRepository = new ConsumedFoodRepository();

        return consumedFoodRepository;
    }
    */




    @Override
    public void createItem(ConsumedFood item, CreateCallback callback) {
        consumedFoodFirebaseRepository.createItem(item, callback);

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
