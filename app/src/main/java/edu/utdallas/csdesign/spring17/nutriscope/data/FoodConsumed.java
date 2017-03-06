package edu.utdallas.csdesign.spring17.nutriscope.data;

import org.threeten.bp.LocalDateTime;

import java.util.UUID;

import io.realm.RealmObject;

/**
 * Created by john on 2/21/17.
 */

public class FoodConsumed  extends RealmObject implements Trackable {
    private String Id = UUID.randomUUID().toString();
    private Food food = null;
    private int weight = 0;

    public FoodConsumed() {

    }


    public FoodConsumed (Food food, int weight, LocalDateTime timeStamp) {
        this.food = food;
        this.weight = weight;

    }

    public Food getFood() {
        return food;
    }

    @Override
    public String getTitle() {
        return food.getName();
    }

    @Override
    public String getId()
    {
        return Id;
    }
}
