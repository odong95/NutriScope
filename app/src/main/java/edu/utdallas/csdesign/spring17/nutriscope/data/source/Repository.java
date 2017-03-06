package edu.utdallas.csdesign.spring17.nutriscope.data.source;

import java.util.List;


/**
 * Created by john on 2/21/17.
 */

public interface Repository<T> {

    interface QueryCallback<T> {

        void onQueryComplete(List<T> items);

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




    void createItem(T item, CreateCallback callback);

    void updateItem(T item, UpdateCallback callback);

    void queryItem(Specification specification, QueryCallback callback);

    void deleteItem(T id, DeleteCallback callback);


}
