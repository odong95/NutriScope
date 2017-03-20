package edu.utdallas.csdesign.spring17.nutriscope.data.history;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

/**
 * Created by john on 3/20/17.
 */

public class HistoryTrackable implements Trackable, Comparable<HistoryTrackable>{

    final private Class<?> listId;
    final private String itemId;

    public HistoryTrackable(Class<?> listId, String itemId) {
        this.listId = listId;
        this.itemId = itemId;
    }

    public Class<?> getListId() {
        return listId;
    }

    public String getItemId() {
        return itemId;
    }

    public int compareTo(HistoryTrackable other) {
        return itemId.compareTo(other.getItemId());
    }

}
