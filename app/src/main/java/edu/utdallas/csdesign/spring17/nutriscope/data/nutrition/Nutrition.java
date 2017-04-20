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

@AutoValue
@IgnoreExtraProperties
public class Nutrition implements Trackable {


    /**
     * This time stamp should be a UNIX Epoch that specifies a day
     */
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
    public void setNutrients(Map<String,Integer> map){this.nutrients = map;}

    @Exclude
    public LocalDate getLocalDate() {
        LocalDateTime t = LocalDateTime.ofEpochSecond(dateStamp, 0, ZoneOffset.UTC);
        return  t.toLocalDate();

    }

    public long getDateStamp(){return dateStamp;}
    public void setDateStamp(Long dateStamp){this.dateStamp=dateStamp;}

}
