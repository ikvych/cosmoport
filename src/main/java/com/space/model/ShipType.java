package com.space.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.beans.PropertyEditorSupport;

public enum ShipType {
    TRANSPORT,
    MILITARY,
    MERCHANT;

    public static PropertyEditorSupport getPropertyEditor(){
        return new PropertyEditorSupport(){
            @Override
            public void setAsText(String dbValue) throws IllegalArgumentException {
                setValue(ShipType.valueOf(dbValue.toUpperCase()));
            }
        };
    }

    @Convert
    public static class PersistJPAConverter implements AttributeConverter<ShipType, String> {
        @Override
        public String convertToDatabaseColumn(ShipType attribute) {
            return attribute.name().toUpperCase();
        }

        @Override
        public ShipType convertToEntityAttribute(String dbData) {
            return ShipType.valueOf(dbData.toUpperCase());
        }
    }
}