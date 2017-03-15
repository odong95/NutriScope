package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.List;

public class Search {

    @SerializedName("list")
    @Expose
    private List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }


}
