package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import com.google.firebase.database.Exclude;

import org.threeten.bp.LocalDate;

import java.util.Map;

public class Nutrition {

    private long dateStamp;
    private Map<String, Integer> nutrients;


    public Nutrition(long dateStamp) {
        this.dateStamp = dateStamp;
    }

    public long getDateStamp() {
        return dateStamp;
    }

    public Map<String, Integer> getNutrients() {
        return nutrients;
    }

    @Exclude
    public LocalDate getLocalDate() {
        return LocalDate.ofEpochDay(dateStamp);

    }

}
