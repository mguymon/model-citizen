package com.tobedevoured.modelcitizen.model;

public class ImmutableModel {

    public final Object objectValue;
    public final String stringValue;
    public final int intValue;
    public final short shortValue;
    public final long longValue;
    public final byte byteValue;
    public final float floatValue;
    public final double doubleValue;
    public final boolean booleanValue;

    public ImmutableModel(Object objectValue,
                          String stringValue,
                          int intValue,
                          short shortValue,
                          long longValue,
                          byte byteValue,
                          float floatValue,
                          double doubleValue,
                          boolean booleanValue) {
        this.objectValue = objectValue;
        this.stringValue = stringValue;
        this.intValue = intValue;
        this.shortValue = shortValue;
        this.longValue = longValue;
        this.byteValue = byteValue;
        this.floatValue = floatValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
    }
}
