package edu.utdallas.csdesign.spring17.nutriscope.data;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 3/10/17.
 */

@AutoValue
@IgnoreExtraProperties
public class ConsumedFood implements Trackable {

    private String ndbNo;
    private String quantity;
    private long dateTimeConsumed;

    public ConsumedFood() {


    }

    public ConsumedFood(long ts) {
        this.dateTimeConsumed = ts;
    }

    public ConsumedFood(String ndbNo, String quantity) {
        this.ndbNo = ndbNo;
        this.quantity = quantity;
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

    @Override
    public long getTimeStamp() {
        return getDateTimeConsumed();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ndbNo", ndbNo);
        result.put("quantity", quantity);
        result.put("dateTimeConsumed", dateTimeConsumed);

        return result;


    }



}
