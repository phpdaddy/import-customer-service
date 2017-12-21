package com.phpdaddy.importcustomerservice.csv;

import com.univocity.parsers.conversions.ObjectConversion;

import java.text.DecimalFormat;
import java.text.ParseException;

public class IntFromFloatWithCommaConversion extends ObjectConversion<Integer> {
    public IntFromFloatWithCommaConversion() {
    }

    public IntFromFloatWithCommaConversion(Integer valueIfStringIsNull, String valueIfObjectIsNull) {
        super(valueIfStringIsNull, valueIfObjectIsNull);
    }

    protected Integer fromString(String input) {
        DecimalFormat format = new DecimalFormat();
        format.setParseBigDecimal(true);
        try {
            return format.parse(input).intValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
