package com.cpjd2.models.metrics;

import org.codehaus.jackson.annotate.JsonTypeName;
@JsonTypeName("RFieldDiagram")
public class RFieldDiagram extends RMetric {

    /**
     * Changing this versionUID will render this class incompatible with older versions.
     */
    public static final long serialVersionUID = 1L;

    private int pictureID;

    /**
     * To save memory, this metric will store the field diagram separately from the drawings, the drawings
     * will be overlayed onto the field diagrams
     */
    private byte[] drawings;

    /**
     * The empty constructor is required for de-serialization
     */
    @SuppressWarnings("unused")
    public RFieldDiagram() {}

    public RFieldDiagram(int ID, int pictureID, byte[] drawings) {
        super(ID, "Field diagram");
        this.pictureID = pictureID;
        this.drawings = drawings;
    }

    @Override
    public String getFormDescriptor() {
        return "Type: Field diagram";
    }

    @Override
    public RMetric clone() {
        return new RFieldDiagram(ID, this.pictureID, drawings);
    }

    @Override
    public String toString() {
        return "";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPictureID() {
        return pictureID;
    }

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }

    public byte[] getDrawings() {
        return drawings;
    }

    public void setDrawings(byte[] drawings) {
        this.drawings = drawings;
    }
}
