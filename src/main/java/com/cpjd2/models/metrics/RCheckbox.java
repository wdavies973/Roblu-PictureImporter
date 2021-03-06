package com.cpjd2.models.metrics;

import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.LinkedHashMap;
import java.util.Map;
@JsonTypeName("RCheckbox")
public class RCheckbox extends RMetric {

    /**
     * Changing this versionUID will render this class incompatible with older versions.
     */
    public static final long serialVersionUID = 1L;
    /**
     * Ordered HashMap containing a title and value for the specified number of elements.
     * The title is treated as the key, so duplicates aren't allowed.
     */
    private LinkedHashMap<String, Boolean> values;

    /**
     * The empty constructor is required for de-serialization
     */
    @SuppressWarnings("unused")
    public RCheckbox() {}

    /**
     * Creates a RCheckbox model
     * @param ID the unique identifier for this object
     * @param title object title
     * @param values non-null, no duplicates map containing title and boolean key pairs
     */
    private RCheckbox(int ID, String title, LinkedHashMap<String, Boolean> values) {
        super(ID, title);
        this.values = values;
        if(this.values == null || values.size() == 0) {
            throw new RuntimeException("RCheckbox must be instantiated with at least 1 item in the LinkedHashMap");
        }
    }

    @Override
    public String getFormDescriptor() {
        StringBuilder descriptor = new StringBuilder("Type: Checkbox\nItems: (key,defaultValue) ");
        for(Object o : values.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            descriptor.append("(").append(pair.getKey()).append(", ").append(pair.getValue()).append(")");
        }
        return descriptor.toString();
    }

    @Override
    public RMetric clone() {
        RCheckbox checkbox = new RCheckbox(ID, title, (LinkedHashMap<String, Boolean>)values.clone());
        return checkbox;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LinkedHashMap<String, Boolean> getValues() {
        return values;
    }

    public void setValues(LinkedHashMap<String, Boolean> values) {
        this.values = values;
    }
}
