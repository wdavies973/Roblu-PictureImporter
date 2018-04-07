package com.cpjd2.models.metrics;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("RTextfield")
public class RTextfield extends RMetric {

    /**
     * Changing this versionUID will render this class incompatible with older versions.
     */
    public static final long serialVersionUID = 1L;

    /**
     * The text contained in this text field
     */
    private String text;

    /**
     * If true, all UI interfaces should detect this flag and only insert numerical content into this field
     */
    private boolean numericalOnly;

    private int ID;

    /**
     * If true, all UI interfaces should load all line breaks onto one, horizontally scrollable text input
     */
    private boolean oneLine;

    /**
     * The empty constructor is required for de-serialization
     */
    @SuppressWarnings("unused")
    public RTextfield() {}

    /**
     * Instantiates a text field object
     * @param ID the unique identifier for this object
     * @param title object title
     * @param text current text
     */
    public RTextfield(int ID, String title, String text) {
        super(ID, title);
        this.text = text;
        this.numericalOnly = false;
        this.oneLine = false;
    }

    /**
     * Instantiates a text field object, with more parameter options
     * @param ID the unique identifier for this object
     * @param title object title
     * @param numericalOnly true to setup a UI flag to only pass numeric input into this class
     * @param oneLine true to setup a UI flag to only allow one line of text
     * @param text current text
     */
    public RTextfield(int ID, String title, boolean numericalOnly, boolean oneLine, String text) {
        super(ID, title);
        this.text = text;
        this.numericalOnly = numericalOnly;
        this.oneLine = oneLine;
    }

    @Override
    public String getFormDescriptor() {
        return "Type: Utils field\nDefault value: "+text;
    }

    @Override
    public RMetric clone() {
        return new RTextfield(ID, title, numericalOnly, oneLine, text);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isNumericalOnly() {
        return numericalOnly;
    }

    public void setNumericalOnly(boolean numericalOnly) {
        this.numericalOnly = numericalOnly;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isOneLine() {
        return oneLine;
    }

    public void setOneLine(boolean oneLine) {
        this.oneLine = oneLine;
    }
}
