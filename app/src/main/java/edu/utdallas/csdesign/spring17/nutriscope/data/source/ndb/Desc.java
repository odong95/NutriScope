package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Desc {

    // NDB food number
    @SerializedName("ndbno") @Expose private String ndbno;

    // food name
    @SerializedName("name")
    @Expose
    private String name;

    // database source
    @SerializedName("ds")
    @Expose
    private String ds;

    // reporting unit (g or ml)
    @SerializedName("ru")
    @Expose
    private String ru;

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getRu() {
        return ru;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

}
