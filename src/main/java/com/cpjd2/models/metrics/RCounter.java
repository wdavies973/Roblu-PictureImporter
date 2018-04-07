package com.cpjd2.models.metrics;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("RCounter")
public class RCounter extends RMetric {

    /**
     * Changing this versionUID will render this class incompatible with older versions.
     */
    public static final long serialVersionUID = 1L;
    /**
     * Represents the current value in integer form
     */
    private double value;

    /**
     * The amount to add to the value each PLUS or MINUS action.
     * Must be positive.
     */
    private double increment;

    /**
     * If true, this class will just load a textfield that is numerical only
     */
    private boolean verboseInput;

    /**
     * The empty constructor is required for de-serialization
     */
    @SuppressWarnings("unused")
    public RCounter() {}

    /**
     * Instantiates an RCounter object
     * @param ID the unique identifier for this object
     * @param title object title
     * @param increment an integer value representing the amount to add or remove to value each time
     * @param value the current integer or double value
     */
    private RCounter(int ID, String title, double increment, double value) {
        super(ID, title);
        this.value = value;
        this.increment = increment;
    }

    /**
     * Add the specified increment to the current value
     */
    public void add() {
        value += increment;
    }

    /**
     * Subtract the specified increment from the current value
     */
    public void minus() {
        value -= increment;
    }

    /**
     * If the RCounter contains a double value, then return a 2 decimal place value, otherwise, return a 0 decimal place value
     * @return the formatted value of this string
     */
    public String getTextValue() {
        return "";
    }

    @Override
    public String getFormDescriptor() {
        return "Type: Counter\nDefault value: "+value+"\nIncrement: "+increment;
    }

    @Override
    public RMetric clone() {
        return null;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getIncrement() {
        return increment;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public boolean isVerboseInput() {
        return verboseInput;
    }

    public void setVerboseInput(boolean verboseInput) {
        this.verboseInput = verboseInput;
    }
}

