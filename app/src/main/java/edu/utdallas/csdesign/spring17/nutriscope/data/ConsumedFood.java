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
public class ConsumedFood {

    private String ndbNo;
    private String quantity;

    public ConsumedFood() {


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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ndbNo", ndbNo);
        result.put("quantity", quantity);

        return result;


    }



}
