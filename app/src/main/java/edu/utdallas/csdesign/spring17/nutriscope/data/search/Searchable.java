package edu.utdallas.csdesign.spring17.nutriscope.data.search;

import java.util.List;

/**
 * Created by john on 3/4/17.
 */

public interface Searchable {


    interface SearchCallback {

        void onSearchComplete(List<Integer> results);

        void onDataNotAvailable();

    }


    void search(String keywords, SearchCallback callback);


}
