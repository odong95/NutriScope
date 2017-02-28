package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

/**
 * Created by john on 2/21/17.
 */

public class HistoryRepository implements HistoryDataSource{

    private static HistoryRepository INSTANCE = null;

    private HistoryRepository() {

    }

    public static HistoryRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new HistoryRepository();
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
