package com.iiita.models;

import org.apache.commons.lang3.RandomStringUtils;

public class AttributeValue{
    Boolean isNegation;
    String variable;
    String value;
    public AttributeValue(Boolean isNegation, String value) {
        this.isNegation = isNegation;
        this.value = value;
        this.variable = RandomStringUtils.randomAlphabetic(6);
    }

    public void setNegation(Boolean negation) {
        isNegation = negation;
    }

    public Boolean getNegation() {
        return isNegation;
    }

    public String getVariable() {
        return variable;
    }

    public String getValue() {
        return value;
    }
}