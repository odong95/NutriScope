package edu.utdallas.csdesign.spring17.nutriscope.addeditfood;

import java.util.Collections;
import java.util.List;

import edu.utdallas.csdesign.spring17.nutriscope.data.source.ndb.json.Measure;

/**
 * Created by john on 3/12/17.
 */

class Quantity {
    final String label = "Quantity";
    private double quantity;
    private int selectedMeasure;
    private final List<Measure> measures;

    Quantity(double quantity, List<Measure> measures) {
        this.quantity = quantity;
        this.measures = measures;
        measures.add(0, Measure.measureGram());
    }

    public List<Measure> getMeasures() {
        return Collections.unmodifiableList(measures);
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getSelectedMeasure() {
        return selectedMeasure;
    }

    public void setSelectedMeasure(int selectedMeasure) {
        this.selectedMeasure = selectedMeasure;
    }
}
