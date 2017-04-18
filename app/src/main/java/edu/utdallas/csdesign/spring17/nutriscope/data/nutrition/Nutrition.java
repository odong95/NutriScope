package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.threeten.bp.LocalDate;

import java.util.Map;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;

@AutoValue
@IgnoreExtraProperties
public class Nutrition implements Trackable {

    private long dateStamp;
    private Map<String, Integer> nutrients;

    public Nutrition() {

    }

    public Nutrition(long dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getKey() {
        return Long.toString(dateStamp);
    }

    public void addNutrient(String nutrient, int amount) {
        nutrients.put(nutrient, amount);
    }

    public int getNutrient(String nutrient) {
        return nutrients.get(nutrient);
    }

    public Map<String, Integer> getNutrients() {
        return nutrients;
    }

    @Exclude
    public LocalDate getLocalDate() {
        return LocalDate.ofEpochDay(dateStamp);

    }

}
