package edu.utdallas.csdesign.spring17.nutriscope.data.nutrition;

import edu.utdallas.csdesign.spring17.nutriscope.data.Specification;

/**
 * Created by Lately on 4/20/2017.
 */

public class NutritionFirebaseSpecification implements Specification {
    long startDay;
    long endDay;

    public NutritionFirebaseSpecification(long startDay, long endDay) {
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public long getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public long getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }
}
