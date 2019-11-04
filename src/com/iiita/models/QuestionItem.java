package com.iiita.models;

public class QuestionItem {
    private Attribute attribute;
    private int frequency;
    private AttributeValue attributeValue;

    public QuestionItem(Attribute attribute, int frequency, AttributeValue attributeValue) {
        this.attribute = attribute;
        this.frequency = frequency;
        this.attributeValue = attributeValue;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public int getFrequency() {
        return frequency;
    }

    public AttributeValue getAttributeValue() {
        return attributeValue;
    }

    @Override
    public String toString() {
        return attribute.getQuestion() + attributeValue.value + "?";
    }
}
