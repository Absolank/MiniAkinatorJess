package com.iiita.models;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;


public class QueryItem {

    private Attribute attribute;
    private ArrayList<AttributeValue> values;
    private String queryAttribute;
    private void genQueryString(){
        StringBuilder sb = new StringBuilder("(");
        sb.append(attribute).append(" ?").append(attribute);
        for(AttributeValue s : values)
        {
            sb.append("&:");
            if(s.isNegation) {
                sb.append("(eq ?");
            }
            else {
                sb.append("(neq ?");
            }
            sb.append(attribute).append(" ?").append(s.variable).append(")");
        }
        sb.append(")");
        queryAttribute = sb.toString();
    }
    public QueryItem(Attribute attribute){
        this.attribute = attribute;
        this.values = new ArrayList<>();
        genQueryString();
    }
    public QueryItem(Attribute attribute, ArrayList<AttributeValue> values) {
        this.attribute = attribute;
        this.values = values;
        genQueryString();
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

    public Attribute getAttribute() {
        return attribute;
    }
}
