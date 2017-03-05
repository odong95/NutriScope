package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

/**
 * Created by john on 2/21/17.
 */

public interface TrackableDataSource {

    interface QueryCallback {

        void onQueryComplete(List<Trackable> items);

        void onDataNotAvailable();

    }

    interface CreateCallback {

        void onCreateComplete();

        void onCreateFailed();

    }

    interface UpdateCallback {

        void onUpdateComplete();

        void onUpdateFailed();

    }

    interface DeleteCallback {

        void onDeleteComplete();

        void onDeleteFailed();

    }




    void createTrackable(Trackable trackable, CreateCallback callback);

    void updateTrackable(String id, Trackable trackable, UpdateCallback callback);

    void queryTrackable(Specification specification, QueryCallback callback);

    void deleteTrackable(String id, DeleteCallback callback);


}
