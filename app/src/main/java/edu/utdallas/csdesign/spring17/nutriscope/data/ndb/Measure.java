package edu.utdallas.csdesign.spring17.nutriscope.data.ndb;


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

}
