package edu.utdallas.csdesign.spring17.nutriscope.data.ndb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by john on 3/5/17.
 */

public class ACResult {
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdString() {
        String id = getId().toString();
        if (id.length() < 5) {
            id = "0" + id;
        }
        return id;
    }

}
