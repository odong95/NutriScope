package edu.utdallas.csdesign.spring17.nutriscope.data;

import org.threeten.bp.LocalDateTime;

import java.util.UUID;

/**
 * Created by john on 2/21/17.
 */

public class FoodConsumed implements Trackable {
    private final String Id = UUID.randomUUID().toString();
    private final Food food;
    private final int weight;
    private final LocalDateTime timeStamp;


    public FoodConsumed (Food food, int weight, LocalDateTime timeStamp) {
        this.food = food;
        this.weight = weight;
        this.timeStamp = timeStamp;

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
