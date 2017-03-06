package edu.utdallas.csdesign.spring17.nutriscope.data.source.realm;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by john on 2/21/17.
 */

public class RealmFoodConsumed extends RealmObject {
    @PrimaryKey
    private String Id = UUID.randomUUID().toString();

    private RealmFood food = null;

    private long quantity = 0;

    private long timeStamp;

    public RealmFoodConsumed() {

    }

    public RealmFoodConsumed(RealmFood food, Long quantity) {
        this.food = food;
        this.timeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        this.quantity = quantity;


    }

    public String getId() {
        return Id;
    }

    public RealmFood getFood() {
        return food;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
