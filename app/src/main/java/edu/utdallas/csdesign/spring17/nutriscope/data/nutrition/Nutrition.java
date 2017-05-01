package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import android.util.Log;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.threeten.bp.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utdallas.csdesign.spring17.nutriscope.data.Trackable;
import edu.utdallas.csdesign.spring17.nutriscope.data.consumedfood.ConsumedFood;
import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Nutrient;

@AutoValue
@IgnoreExtraProperties
public class Nutrition implements Trackable {


    private static final String TAG = "Nutrition";

    /**
     * Timezones can be tricky with an app like this,
     * let's use Java.Time toEpochDay() to specify the day
     */
    private long dateStamp;
    private Map<String, Double> nutrients = new HashMap<>();

    public Nutrition() {

    }

    public Nutrition(long dateStamp) {
        this();
        this.dateStamp = dateStamp;
    }

    public String getKey() {
        return Long.toString(dateStamp);
    }

    public void addNutrient(String nutrient, double amount) {
        nutrients.put(nutrient, amount);
    }

    public double getNutrient(String nutrient) {
        return nutrients.get(nutrient);
    }

    public Map<String, Double> getNutrients() {
        return nutrients;
    }
    public void setNutrients(Map<String, Double> map){this.nutrients = map;}

    @Exclude
    public LocalDate getLocalDate() {
        return LocalDate.ofEpochDay(dateStamp);
    }

    public long getDateStamp(){return dateStamp;}
    public void setDateStamp(Long dateStamp){this.dateStamp=dateStamp;}

    public static Nutrition ndbToNutrition(long datestamp, ConsumedFood consumedFood) {

        // ndb measure nutrition per 100 grams
        double quantity = consumedFood.getQuantity() / 100;

        List<Nutrient> nutrients = consumedFood.getFood().getNutrients();

        Nutrition nutrition = new Nutrition(datestamp);

        for(Nutrient nutrient: nutrients) {
            if (nutrient.getUnit().equals("g")) {
                nutrition.addNutrient(nutrient.getNutrientId(),
                        Double.parseDouble(nutrient.getValue()) * quantity);
            }
            else if (nutrient.getUnit().equals("mg")) {
                nutrition.addNutrient(nutrient.getNutrientId(),
                        Double.parseDouble(nutrient.getValue()) * quantity / 1000);
            }
            else if (nutrient.getUnit().equals("µg")) {
                nutrition.addNutrient(nutrient.getNutrientId(),
                        Double.parseDouble(nutrient.getValue()) * quantity / (1000*1000));
            }
            else if (nutrient.getUnit().equals("kcal")) {
                nutrition.addNutrient(nutrient.getNutrientId(),
                        Double.parseDouble(nutrient.getValue()));
            }
            else {
                Log.w(TAG, "unsupported unit " + nutrient.getUnit());
            }
        }

        return nutrition;
    }


}
