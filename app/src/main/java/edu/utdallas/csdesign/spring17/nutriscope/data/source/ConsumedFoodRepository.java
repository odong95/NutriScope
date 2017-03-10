package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.utdallas.csdesign.spring17.nutriscope.data.ConsumedFood;

/**
 * Created by john on 3/10/17.
 */

@Module
public class ConsumedFoodRepository implements Repository<ConsumedFood> {

    ConsumedFoodRepository consumedFoodRepository;

    public ConsumedFoodRepository() {

    }

    @Provides
    @Singleton
    public ConsumedFoodRepository getConsumedFoodRepository() {
        consumedFoodRepository = new ConsumedFoodRepository();
        return consumedFoodRepository;
    }




    @Override
    public void createItem(ConsumedFood item, CreateCallback callback) {

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
