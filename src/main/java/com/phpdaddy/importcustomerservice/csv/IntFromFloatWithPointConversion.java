package com.phpdaddy.importcustomerservice.csv;

import com.univocity.parsers.conversions.ObjectConversion;

public class IntFromFloatWithPointConversion extends ObjectConversion<Integer> {
    public IntFromFloatWithPointConversion() {
    }

    public IntFromFloatWithPointConversion(Integer valueIfStringIsNull, String valueIfObjectIsNull) {
        super(valueIfStringIsNull, valueIfObjectIsNull);
    }

    protected Integer fromString(String input) {
        return Float.valueOf(input).intValue();
    }
}
