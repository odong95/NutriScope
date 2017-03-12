package edu.utdallas.csdesign.spring17.nutriscope.data.search;

import java.util.Arrays;
import java.util.List;

/**
 * Created by john on 3/4/17.
 */

public class SillySearch implements Searchable {

    @Override
    public void search(String keywords, SearchCallback callback) {
        List<Integer> results = Arrays.asList(1);


    }

}
