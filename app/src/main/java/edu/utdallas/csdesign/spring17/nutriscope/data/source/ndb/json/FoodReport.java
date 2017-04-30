package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodReport {

    @SerializedName("foods")
    @Expose
    private List<Food_> foods = null;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("notfound")
    @Expose
    private Integer notfound;
    @SerializedName("api")
    @Expose
    private Double api;

    public List<Food_> getFoods() {
        return foods;
    }

    public void setFoods(List<Food_> foods) {
        this.foods = foods;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getNotfound() {
        return notfound;
    }

    public void setNotfound(Integer notfound) {
        this.notfound = notfound;
    }

    public Double getApi() {
        return api;
    }

    public void setApi(Double api) {
        this.api = api;
    }

}
