package com.cpjd2.models.metrics;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("RBoolean")
public class RBoolean extends RMetric {

    /**
     * Changing this versionUID will render this class incompatible with older versions.
     */
    public static final long serialVersionUID = 1L;

    private boolean value;

    /**
     * The empty constructor is required for de-serialization
     */
    @SuppressWarnings("unused")
    public RBoolean() {}

    /**
     * Instantiates a boolean model
     * @param ID the unique identifier for this object
     * @param title object title
     * @param value boolean value to store
     */
    private RBoolean(int ID, String title, boolean value) {
        super(ID, title);
        this.value = value;
    }

    @Override
    public String getFormDescriptor() {
        return "Type: Boolean\nDefault value: "+value;
    }

    @Override
    public RMetric clone() {
        RBoolean bool = new RBoolean(ID, title, value);
        return bool;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
