package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;

import java.util.Map;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.FoodNutrients;

@AutoValue
@IgnoreExtraProperties
public class Nutrition implements Trackable {

    private long dateStamp;
    private Map<String, Float> nutrients;

    public Nutrition() {

    }

    public Nutrition(long dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getKey() {
        return Long.toString(dateStamp);
    }

    public void addNutrient(String nutrient, float amount) {
        nutrients.put(nutrient, amount);
    }

    public float getNutrient(String nutrient) {
        return nutrients.get(nutrient);
    }

    public Map<String, Float> getNutrients() {
        return nutrients;
    }
    public void setNutrients(Map<String,Float> map){this.nutrients = map;}

    @Exclude
    public LocalDate getLocalDate() {
        return LocalDate.ofEpochDay(dateStamp);
    }

    public long getDateStamp(){return dateStamp;}
    public void setDateStamp(Long dateStamp){this.dateStamp=dateStamp;}


}
