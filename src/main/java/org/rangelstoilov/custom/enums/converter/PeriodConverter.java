package org.rangelstoilov.custom.enums.converter;

import org.rangelstoilov.custom.enums.Period;

import java.beans.PropertyEditorSupport;

public class PeriodConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String capitalized = text.toUpperCase();
        try {
        Period period = Period.valueOf(capitalized);
        setValue(period);
        } catch (IllegalArgumentException e){
            setValue(null);
        }
    }
}
