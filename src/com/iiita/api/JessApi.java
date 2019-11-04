package com.iiita.api;

import com.iiita.models.HarryPotterCharacter;
import com.iiita.models.Query;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jess.*;

import java.io.*;
import java.util.LinkedList;

public class JessApi {
    private static JessApi jessApi;
    private Rete rete;
    private JessApi() throws IOException, JessException, CsvValidationException {
        rete = new Rete();
        rete.watchAll();
        String characterTemplate = "(deftemplate character (slot name) (slot gender) (slot job) (slot house) (slot wand) (slot patronus) (slot species) (slot blood_status) (slot hair_color) (slot eye_color))";
        rete.eval(characterTemplate);
        File file = new File("Characters.csv");
        CSVReader csvReader = new CSVReader(new FileReader(file));
        LinkedList<HarryPotterCharacter> list = new LinkedList<>();
        String[] arr;
        csvReader.readNext();
        while((arr = csvReader.readNext()) != null){
            list.add(new HarryPotterCharacter(arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10]));
        }
        for(HarryPotterCharacter hpc : list) {
            insertCharacterToFactDatabase(hpc, rete);
        }
    }
    public static synchronized JessApi getInstance() throws CsvValidationException, JessException, IOException {
        if(jessApi == null)
            jessApi = new JessApi();
        return jessApi;
    }
    private void insertCharacterToFactDatabase(HarryPotterCharacter hpc, Rete rete) throws JessException {
        Fact f = new Fact("character", rete);
        f.setSlotValue("name", new Value(hpc.getName(), RU.STRING));
        f.setSlotValue("gender", new Value(hpc.getGender(), RU.STRING));
        f.setSlotValue("job", new Value(hpc.getJob(), RU.STRING));
        f.setSlotValue("house", new Value(hpc.getHouse(), RU.STRING));
        f.setSlotValue("wand", new Value(hpc.getWand(), RU.STRING));
        f.setSlotValue("patronus", new Value(hpc.getPatronus(), RU.STRING));
        f.setSlotValue("species", new Value(hpc.getSpecies(), RU.STRING));
        f.setSlotValue("hair_color", new Value(hpc.getHairColor(), RU.STRING));
        f.setSlotValue("blood_status", new Value(hpc.getBloodStatus(), RU.STRING));
        f.setSlotValue("eye_color", new Value(hpc.getEyeColor(), RU.STRING));
        rete.assertFact(f);
    }
    private HarryPotterCharacter toHarryPotterCharacter(QueryResult queryResult) throws JessException {
        return new HarryPotterCharacter(
                queryResult.getString("name"),
                queryResult.getString("gender"),
                queryResult.getString("job"),
                queryResult.getString("house"),
                queryResult.getString("wand"),
                queryResult.getString("patronus"),
                queryResult.getString("species"),
                queryResult.getString("hair_color"),
                queryResult.getString("blood_status"),
                queryResult.getString("eye_color")
        );
    }

    public LinkedList<HarryPotterCharacter> getPrediction(Query query) throws JessException {
        rete.eval(query.toString());
        LinkedList<HarryPotterCharacter> characterResults = new LinkedList<>();
        QueryResult results = rete.runQueryStar("temp-search", query.getValueVector());
        while(results.next()) {
            characterResults.add(toHarryPotterCharacter(results));
        }
        return characterResults;
    }
}
