package edu.utdallas.csdesign.spring17.nutriscope.data.history;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/18/17.
 */

@Module
public class HistoryRepositoryModule {

    private final HistoryRepository historyRepository;

    public HistoryRepositoryModule(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Provides
    HistoryRepository providesHistoryRepository() {
        return historyRepository;
    }


}

