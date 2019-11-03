package com.iiita;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jess.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class JessApi {
    Rete r;
    LinkedList<HarryPotterCharacter> list;
    String query;
    String rule, rule1;
    String type = "(deftemplate character (slot name) (slot gender) (slot job) (slot house) (slot wand) (slot patronus) (slot species) (slot blood_status) (slot hair_color) (slot eye_color) (multislot loyalty) (multislot skills))";
    String[] queries;
    JessApi() throws IOException, JessException, CsvValidationException {
        r = new Rete();
        r.watchAll();
        r.eval(type);
        query = "(defquery search " +
                "\"Finds people\" " +
                "(declare (variables ?n ?g ?j ?h ?w ?s ?bs ?hc ?ec)) " +
                "?character <- (character (name ?n) (gender ?g) (job ?j) (house ?h) (wand ?w) (species ?s) (blood_status ?bs) (hair_color ?hc) (eye_color ?ec)))";
        rule = "(defquery temp-search " +
                "\"Finds People\"" +
                "(declare (variables ?g)) "+
                "?character <- (character (name ?n) (gender ?g))) ";

//        r.eval(rule);
        rule1 = "(defquery temp-search " +
                "\"finds People\" " +
                "(declare (variables ?nam ?ne ?gen))" +
                "?character <- (character (name ?n&:(neq ?n ?nam)&:(neq ?n ?ne)) (gender ?g&:(neq ?g ?gen)) ))";
        r.eval(query);
//        r.batch("query.clp");
        File file = new File("Characters.csv");
        CSVReader csvReader = new CSVReader(new FileReader(file));
        list = new LinkedList<>();
        String[] arr;
        csvReader.readNext();
        while((arr = csvReader.readNext()) != null){
            list.add(new HarryPotterCharacter(arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12]));
        }
        for(HarryPotterCharacter hpc : list) {
            Fact f = new Fact("character", r);
            f.setSlotValue("name", new Value(hpc.getName(), RU.STRING));
            f.setSlotValue("gender", new Value(hpc.getGender(), RU.STRING));
            f.setSlotValue("job", new Value(hpc.getJob(), RU.LIST));
            f.setSlotValue("house", new Value(hpc.getHouse(), RU.STRING));
            f.setSlotValue("wand", new Value(hpc.getWand(), RU.STRING));
            f.setSlotValue("patronus", new Value(hpc.getPatronus(), RU.STRING));
            f.setSlotValue("species", new Value(hpc.getSpecies(), RU.STRING));
            f.setSlotValue("hair_color", new Value(hpc.getHairColor(), RU.STRING));
            f.setSlotValue("blood_status", new Value(hpc.getBloodStatus(), RU.STRING));
            f.setSlotValue("eye_color", new Value(hpc.getEyeColor(), RU.STRING));
            f.setSlotValue("loyalty", new Value(hpc.getloyalty(), RU.LIST));
            f.setSlotValue("skills", new Value(hpc.getSkills(), RU.LIST));
            r.assertFact(f);
        }
//        QueryResult result = r.runQueryStar("search-by-gender", new ValueVector().add(new Value("Male", RU.STRING)));

//        while (result.next()) {
//            System.out.println(result.getString("n"));
//        }
    }
    ArrayList<String> getPrediction(ArrayList<String> strings) throws JessException, IOException {
        r.eval(rule1);
        ValueVector v = new ValueVector();
        for(String s : strings)
            v.add(new Value(s, RU.STRING));
        String result = null;
        QueryResult res = r.runQueryStar("temp-search", v);
        FileWriter fileWriter = new FileWriter(new File("x.txt"));
        r.addOutputRouter("t", fileWriter);
        ArrayList<String> results = new ArrayList<>();
        while(res.next()){
            result = res.getString("n");
            results.add(result);
        }
        return results;
    }
}
