package edu.utdallas.csdesign.spring17.nutriscope.data.ndb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("food")
    @Expose
    private Food_ food;

    public Food_ getFood() {
        return food;
    }

    public void setFood(Food_ food) {
        this.food = food;
    }

}
