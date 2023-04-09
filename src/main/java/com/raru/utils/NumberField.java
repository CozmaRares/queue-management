package com.raru.utils;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

public class NumberField extends JFormattedTextField {
    private final static NumberFormatter numberFormatter;

    static {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);

        numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Long.class);
        numberFormatter.setAllowsInvalid(false);
    }

    public NumberField(int defaultValue) {
        super(numberFormatter);
        setValue(defaultValue);
    }
}
