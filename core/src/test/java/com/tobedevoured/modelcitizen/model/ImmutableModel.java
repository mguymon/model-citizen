package com.tobedevoured.modelcitizen.model;

public class ImmutableModel {

    public final Object objectValue;
    public final int intValue;
    public final short shortValue;
    public final long longValue;
    public final byte byteValue;
    public final float floatValue;
    public final double doubleValue;
    public final boolean booleanValue;

    private final String privateField;

    public ImmutableModel(Object objectValue,
                          int intValue,
                          short shortValue,
                          long longValue,
                          byte byteValue,
                          float floatValue,
                          double doubleValue,
                          boolean booleanValue,
                          String privateField) {
        this.objectValue = objectValue;
        this.intValue = intValue;
        this.shortValue = shortValue;
        this.longValue = longValue;
        this.byteValue = byteValue;
        this.floatValue = floatValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.privateField = privateField;
    }

    public String getPrivateField() {
        return privateField;
    }
}
