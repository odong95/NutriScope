package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

/**
 * Created by john on 2/21/17.
 */

public interface HistoryDataSource {

    interface LoadHistoryCallback {

        void onHistoryLoaded(List<Trackable> history);

        void onDataNotAvailable();

    }

    interface GetEventCallback {

        void onEventLoaded(Trackable event);

        void onDataNotAvailable();

    }



    void getHistory(LoadHistoryCallback callback);

    void getHistoryEvent(String eventId, GetEventCallback callback);

    void saveEvent(Trackable event);

}
