package edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food_ {

    @SerializedName("food")
    @Expose
    private Food food;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

}
