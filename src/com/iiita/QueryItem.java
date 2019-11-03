package com.iiita;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;

import java.util.ArrayList;
import java.util.Random;


class AttributeValue{
    Boolean isNegation;
    String variable;
    String value;
    public AttributeValue(Boolean isNegation, String value) {
        this.isNegation = isNegation;
        this.value = value;
        this.variable = RandomStringUtils.randomAlphabetic(6);
    }
}
public class QueryItem {

    private String attribute;
    private ArrayList<AttributeValue> values;
    ArrayList<String> variables;
    private String queryAttribute;
    private void genQueryString(){
        StringBuilder sb = new StringBuilder("(");
        sb.append(attribute).append(" ?").append(attribute);
        for(AttributeValue s : values)
        {
            sb.append("&:");
            if(s.isNegation) {
                sb.append("(neq ?");
            }
            else {
                sb.append("(eq ?");
            }
            sb.append(attribute).append(" ?").append(s.variable).append(")");
        }
        sb.append(")");
        queryAttribute = sb.toString();
    }
    public QueryItem(String attribute){
        this.attribute = attribute;
        this.values = new ArrayList<>();
    }
    public QueryItem(String attribute, ArrayList<AttributeValue> values) {
        this.attribute = attribute;
        this.values = values;
    }
    public void addValue(boolean neg, String value){
        values.add(new AttributeValue(neg, value));
        genQueryString();
    }
    public String toString() {
        return queryAttribute;
    }

    public ArrayList<AttributeValue> getValues() {
        return values;
    }

    public String toQueryString(){
        return queryAttribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
