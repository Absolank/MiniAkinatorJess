package com.iiita.models;

import jess.JessException;
import jess.RU;
import jess.Value;
import jess.ValueVector;

import java.util.TreeMap;

public class Query {

    private String queryString;
    private TreeMap<Attribute, QueryItem> queryItems = new TreeMap<>();

    private ValueVector valueVector;

    private void genQueryString() throws JessException {
        StringBuilder sb = new StringBuilder("(defquery temp-search ").append(" \"Finds Character\" ");
        StringBuilder temp = new StringBuilder(" (declare (variables ");
        StringBuilder temp1 = new StringBuilder(" ?character <- (character ");
        valueVector = new ValueVector();
        for(QueryItem queryItem : queryItems.values())
        {
            for(AttributeValue attributeValue : queryItem.getValues()) {
                temp.append(" ?").append(attributeValue.variable).append(" ");
                valueVector.add(new Value(attributeValue.value, RU.STRING));
            }
            temp1.append(queryItem.toString()).append(" ");
        }
        sb.append(temp).append(")) ").append(temp1).append(")").append(")");
        queryString = sb.toString();
    }
    public Query() throws JessException {
        for(Attribute s : Attribute.values()) {
            queryItems.put(s, new QueryItem(s));
        }
        genQueryString();
    }

    public void add(Attribute attribute, boolean neg, String value) throws JessException {
        queryItems.get(attribute).addValue(neg, value);
        genQueryString();
    }

    public ValueVector getValueVector() {
        return valueVector;
    }

    @Override
    public String toString() {
        return queryString;
    }
}
