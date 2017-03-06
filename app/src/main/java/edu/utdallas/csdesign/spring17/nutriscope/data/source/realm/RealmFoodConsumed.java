package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import org.threeten.bp.LocalDateTime;

import java.util.UUID;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;
import io.realm.RealmObject;

/**
 * Created by john on 2/21/17.
 */

public class RealmFoodConsumed extends RealmObject implements Trackable {
    private String Id = UUID.randomUUID().toString();
    private RealmFood food = null;
    private int weight = 0;

    public RealmFoodConsumed() {

    }


    public RealmFoodConsumed(RealmFood food, int weight, LocalDateTime timeStamp) {
        this.food = food;
        this.weight = weight;

    }

    public RealmFood getFood() {
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
