package com.iiita.api;

import com.iiita.api.JessApi;
import com.iiita.models.*;
import com.iiita.models.QuestionItem;
import com.opencsv.exceptions.CsvValidationException;
import jess.JessException;

import java.io.IOException;
import java.util.*;

public class QuestionEngine {
    private JessApi jessApi;
    private Query query;
    private TreeMap<String, Integer>[] maps;
    private LinkedList<QuestionItem> questionItems;
    private HarryPotterCharacter answer;
    private HashSet<String> askedValues  = new HashSet<>();
    private HashSet<String> askedAttributes = new HashSet<>();
    public QuestionEngine() throws CsvValidationException, JessException, IOException {
        jessApi = JessApi.getInstance();
        query = new Query();
        maps = new TreeMap[9];
        for(int i = 0; i < 9; i++)
            maps[i] = new TreeMap<String, Integer>();
        questionItems = new LinkedList<>();
    }
    private void put(TreeMap<String, Integer> map, String s) {
        if(map.containsKey(s))
            map.put(s, map.get(s) + 1);
        else
            map.put(s, 1);
    }
    public LinkedList<HarryPotterCharacter> askQuestion() throws JessException {
        return jessApi.getPrediction(query);
    }
    private LinkedList<HarryPotterCharacter> harryPotterCharacters;
    public QuestionItem getNextQuestion() throws Exception {
        clear();
        harryPotterCharacters = jessApi.getPrediction(query);
        if(harryPotterCharacters.size() == 1) {
            answer = harryPotterCharacters.getLast();
//            throw new Exception("Answer reached");
            return null;
        }
        else if(harryPotterCharacters.size() != 0) {
            for (HarryPotterCharacter hpc : harryPotterCharacters) {
                put(maps[0], hpc.getGender());
                put(maps[1], hpc.getJob());
                put(maps[2], hpc.getHouse());
                put(maps[3], hpc.getWand());
                put(maps[4], hpc.getPatronus());
                put(maps[5], hpc.getSpecies());
                put(maps[6], hpc.getBloodStatus());
                put(maps[7], hpc.getHairColor());
                put(maps[8], hpc.getEyeColor());
            }
            Attribute attr = null;
            String value = null;
            int frequency = 0;
            Attribute[] attributes = Attribute.values();
            for (int i = 1; i < attributes.length; i++) {
                if(!askedAttributes.contains(attributes[i].toString()))
                for (Map.Entry<String, Integer> e : maps[attributes[i].getIndex() - 1].entrySet()) {
                    if (frequency < e.getValue() && !askedValues.contains(e.getKey())) {
                        attr = attributes[i];
                        frequency = e.getValue();
                        value = e.getKey();
                    }
                }
            }

            QuestionItem item = null;
            if (value !=  null) {
                item = new QuestionItem(attr, frequency, new AttributeValue(false, value));
                questionItems.add(item);
            }
            return item;
        }
        return null;
    }
    public void pushAnswer(boolean answer) throws JessException {
        QuestionItem lastQuestion = questionItems.getLast();
        askedValues.add(lastQuestion.getAttributeValue().getValue());
        lastQuestion.getAttributeValue().setNegation(answer);
        query.add(lastQuestion.getAttribute(), lastQuestion.getAttributeValue().getNegation(), lastQuestion.getAttributeValue().getValue());
        if(answer)
            askedAttributes.add(lastQuestion.getAttribute().toString());
    }

    public void clear(){
        for (TreeMap<String, Integer> map : maps) map.clear();
    }
    public HarryPotterCharacter getAnswer(){
        return answer;
    }
}
