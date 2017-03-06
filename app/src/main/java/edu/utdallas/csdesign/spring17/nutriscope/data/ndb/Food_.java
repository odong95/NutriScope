package edu.utdallas.csdesign.spring17.nutriscope.data.ndb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Food_ {

    @SerializedName("sr")
    @Expose
    private String sr;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("desc")
    @Expose
    private Desc desc;
    @SerializedName("nutrients")
    @Expose
    private List<Nutrient> nutrients = null;
    @SerializedName("footnotes")
    @Expose
    private List<Object> footnotes = null;

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Desc getDesc() {
        return desc;
    }

    public void setDesc(Desc desc) {
        this.desc = desc;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    public List<Object> getFootnotes() {
        return footnotes;
    }

    public void setFootnotes(List<Object> footnotes) {
        this.footnotes = footnotes;
    }

}
