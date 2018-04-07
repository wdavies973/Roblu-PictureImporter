package com.cpjd2.models.metrics;

import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * RFieldDiagram stores match data from TheBlueAlliance.com, this metric is always uneditable
 */
@JsonTypeName("RFieldData")
public class RFieldData extends RMetric {

    /**
     * Changing this versionUID will render this class incompatible with older versions.
     */
    public static final long serialVersionUID = 1L;

    /**
     * The string is the metric name, the ArrayList stores 2 metrics (red first, blue second)
     * representing the match data.
     */
    private LinkedHashMap<String, ArrayList<RMetric>> data;

    public RFieldData() {}

    public RFieldData(int ID, String title) {
        super(ID, title);
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public String getFormDescriptor() {
        return "Type: Field data";
    }

    @Override
    public RMetric clone() {
        RFieldData fieldData = new RFieldData(ID, title);
        fieldData.setData(data);
        return fieldData;
    }

    @Override
    public String toString() {
        return "";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LinkedHashMap<String, ArrayList<RMetric>> getData() {
        return data;
    }

    public void setData(LinkedHashMap<String, ArrayList<RMetric>> data) {
        this.data = data;
    }
}
