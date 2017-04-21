package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Measure {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("eqv")
    @Expose
    private Double eqv;
    @SerializedName("eunit")
    @Expose
    private String eunit;
    @SerializedName("qty")
    @Expose
    private Double qty;
    @SerializedName("value")
    @Expose
    private String value;

    public Measure() {

    }

    public Measure(String label, Double eqv, String eunit, Double qty, String value) {
        this.label = label;
        this.eqv = eqv;
        this.eunit = eunit;
        this.qty = qty;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getEqv() {
        return eqv;
    }

    public void setEqv(Double eqv) {
        this.eqv = eqv;
    }

    public String getEunit() {
        return eunit;
    }

    public void setEunit(String eunit) {
        this.eunit = eunit;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    static public Measure measureGram() {
        return new Measure("grams", 1.0, "g", 1.0, "");
    }


}
