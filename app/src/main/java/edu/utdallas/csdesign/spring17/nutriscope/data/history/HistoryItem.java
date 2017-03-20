package edu.utdallas.csdesign.spring17.nutriscope.data.history;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

/**
 * Created by john on 3/20/17.
 */

public class HistoryItem implements Trackable, Comparable<HistoryItem>{


    final private String key;
    final private Class<?> type;


    public HistoryItem(Class<?> type, String key) {
        this.type = type;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Class<?> getType() {
        return type;
    }

    public int compareTo(HistoryItem other) {
        return key.compareTo(other.getKey());
    }

}
