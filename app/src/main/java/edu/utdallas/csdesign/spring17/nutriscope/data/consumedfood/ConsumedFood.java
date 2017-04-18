package edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Food;
import edu.utdallas.csdesign.spring17.nutriscope.util.KeyGenerator;

/**
 * Created by john on 3/10/17.
 */

@AutoValue
@IgnoreExtraProperties
public class ConsumedFood implements Trackable {

    private Food food;
    private String ndbNo;
    private String quantity;
    private long dateTimeConsumed;

    public ConsumedFood() {


    }

    public ConsumedFood(Food food, String ndbNo, String quantity, long dateTimeConsumed) {
        this.food = food;
        this.ndbNo = ndbNo;
        this.quantity = quantity;
        this.dateTimeConsumed = dateTimeConsumed;
    }

    public String getNdbNo() {
        return ndbNo;
    }

    public String getQuantity() {
        return quantity;
    }

    public long getDateTimeConsumed() {
        return dateTimeConsumed;
    }

    public void setDateTimeConsumed(long dateTimeConsumed) {
        this.dateTimeConsumed = dateTimeConsumed;
    }

    @Exclude
    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ndbNo", ndbNo);
        result.put("quantity", quantity);
        result.put("dateTimeConsumed", dateTimeConsumed);

        return result;


    }

    @Override
    public String getKey() {
        return KeyGenerator.generate(dateTimeConsumed);
    }

}
