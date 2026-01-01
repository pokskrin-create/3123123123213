package org.apache.tika.metadata;

import org.apache.tika.metadata.Property;

/* loaded from: classes4.dex */
public final class PropertyTypeException extends IllegalArgumentException {
    public PropertyTypeException(String str) {
        super(str);
    }

    public PropertyTypeException(Property.PropertyType propertyType, Property.PropertyType propertyType2) {
        super("Expected a property of type " + propertyType + ", but received " + propertyType2);
    }

    public PropertyTypeException(Property.ValueType valueType, Property.ValueType valueType2) {
        super("Expected a property with a " + valueType + " value, but received a " + valueType2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public PropertyTypeException(Property.PropertyType propertyType) {
        String str;
        if (propertyType != Property.PropertyType.COMPOSITE) {
            str = propertyType + " is not supported";
        } else {
            str = "Composite Properties must not include other Composite Properties as either Primary or Secondary";
        }
        super(str);
    }
}
