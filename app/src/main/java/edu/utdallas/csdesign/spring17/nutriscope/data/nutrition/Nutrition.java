package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Nutrient;

@AutoValue
@IgnoreExtraProperties
public class Nutrition implements Trackable {


    /**
     * Timezones can be tricky with an app like this,
     * let's use Java.Time toEpochDay() to specify the day
     */
    private long dateStamp;
    private Map<String, Float> nutrients = new HashMap<>();

    public Nutrition() {

    }

    public Nutrition(long dateStamp) {
        this();
        this.dateStamp = dateStamp;
    }

    public String getKey() {
        return Long.toString(dateStamp);
    }

    public void addNutrient(String nutrient, Float amount) {
        nutrients.put(nutrient, amount);
    }

    public Float getNutrient(String nutrient) {
        return nutrients.get(nutrient);
    }

    public Map<String, Float> getNutrients() {
        return nutrients;
    }
    public void setNutrients(Map<String, Float> map){this.nutrients = map;}

    @Exclude
    public LocalDate getLocalDate() {
        LocalDateTime t = LocalDateTime.ofEpochSecond(dateStamp, 0, ZoneOffset.UTC);
        return  t.toLocalDate();

    }

    public long getDateStamp(){return dateStamp;}
    public void setDateStamp(Long dateStamp){this.dateStamp=dateStamp;}


    public static Nutrition ndbToNutrition(long datestamp, List<Nutrient> nutrients) {

        Nutrition nutrition = new Nutrition(datestamp);

        for(Nutrient nutrient: nutrients) {
            if (nutrient.getUnit().equals("g")) {
                nutrition.addNutrient(nutrient.getNutrientId(),
                                      Float.parseFloat(nutrient.getValue()));
            }
        }

        return nutrition;
    }



}
