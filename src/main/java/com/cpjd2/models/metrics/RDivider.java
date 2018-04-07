package com.cpjd2.models.metrics;

import org.codehaus.jackson.annotate.JsonTypeName;


@JsonTypeName("RDivider")
public class RDivider extends RMetric {
    /**
     * Changing this versionUID will render this class incompatible with older versions.
     */
    public static final long serialVersionUID = 1L;
    /**
     * The empty constructor is required for de-serialization
     */
    public RDivider() {}

    public RDivider(int ID, String title) {
        super(ID, title);
    }

    @Override
    public String getFormDescriptor() {
        return "Type: Divider\nDefault value: "+title;
    }

    @Override
    public RMetric clone() {
        return new RDivider(ID, title);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
