package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.Food;
import edu.utdallas.csdesign.spring17.nutriscope.data.FoodConsumed;
import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

/**
 * Created by john on 2/21/17.
 */

public class TrackableRepository implements TrackableDataSource {

    private static TrackableRepository INSTANCE = null;

    private TrackableRepository() {

        historyCache.add(new FoodConsumed(new Food("Pizza", 50, 50, 50, 50),100, LocalDateTime.now()));

    }

    public static TrackableRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TrackableRepository();
        }

        return INSTANCE;
    }


    private List<Trackable> historyCache = new ArrayList<>();



    public void getHistory(LoadHistoryCallback callback) {
        callback.onHistoryLoaded(historyCache);
    }

    public void getHistoryEvent(String eventId, GetEventCallback callback) {
        for(Trackable track: historyCache) {
            if (track.getId().equals(eventId)) {
                callback.onEventLoaded(track);
            }

        }
        callback.onDataNotAvailable();

    }

    public void saveEvent(Trackable event) {
        historyCache.add(event);

    }




}
