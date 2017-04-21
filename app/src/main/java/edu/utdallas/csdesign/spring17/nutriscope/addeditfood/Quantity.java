package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import java.util.Collections;
import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Measure;

/**
 * Created by john on 3/12/17.
 */

class Quantity {
    final String label = "Quantity";
    final String quantity;
    final List<Measure> measures;

    Quantity(String quantity, List<Measure> measures) {
        this.quantity = quantity;
        this.measures = measures;
    }

    public List<Measure> getMeasures() {
        return Collections.unmodifiableList(measures);
    }
}
