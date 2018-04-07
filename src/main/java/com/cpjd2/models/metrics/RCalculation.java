package com.cpjd2.models.metrics;

import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;


/**
 * RCalculation supports custom calculations based off other custom metrics.
 * It only supports metrics based off the RSlider, RCounter, and RStopwatch metrics.
 */
@JsonTypeName("RSlider")
public class RCalculation extends RMetric {
    /**
     * Changing this versionUID will render this class incompatible with older versions.
     */
    public static final long serialVersionUID = 1L;

    /**
     * Stores the last value, call getValue() to update this.
     * toString() will return this
     */
    private double lastValue;

    /**
     * The equation string
     */
    private String calculation;

    /**
     * The empty constructor is required for de-serialization
     */
    @SuppressWarnings("unused")
    public RCalculation() {}

    /**
     * Instantiates a boolean model
     * @param ID the unique identifier for this object
     * @param title object title
     */
    public RCalculation(int ID, String title) {
        super(ID, title);
    }

    @Override
    public String getFormDescriptor() {
        return "Type: Calculation metric\nCalculation: "+calculation;
    }

    @Override
    public RMetric clone() {
        RCalculation metric = new RCalculation(ID, title);
        metric.setCalculation(calculation);
        return metric;
    }

    /**
     * Process the equation and returns a value
     * @param metrics the metric list to process
     * @return the value
     */
    public String getValue(ArrayList<RMetric> metrics) {
        return "";
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(lastValue);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getLastValue() {
        return lastValue;
    }

    public void setLastValue(double lastValue) {
        this.lastValue = lastValue;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }
}
