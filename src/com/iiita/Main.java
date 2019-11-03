package com.iiita;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;

class QuestionItem {
    String value;
    int frequency;
    String attribute;

    public QuestionItem(String value, int frequency, String attribute) {
        this.value = value;
        this.frequency = frequency;
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        if (attribute.compareTo("gender") == 0) {
            return "Is the character's gender: " + value + " ?";
        }
        if (attribute.compareTo("job") == 0) {
            return "Is the character's occupation: " + value + " ?";
        }
        if (attribute.compareTo("house") == 0) {
            return "Is the character's house: " + value + " ?";
        }
        if (attribute.compareTo("wand") == 0) {
            return "Is the character's wand: " + value + " ?";
        }
        if (attribute.compareTo("patronus") == 0) {
            return "Is the character's patronus: " + value + " ?";
        }
        if (attribute.compareTo("species") == 0) {
            return "Is the character's species: " + value + " ?";
        }
        if (attribute.compareTo("bloodStatus") == 0) {
            return "Is the character's blood status: " + value + " ?";
        }
        if (attribute.compareTo("hairColor") == 0) {
            return "Is the character's hair color: " + value + " ?";
        }
        if (attribute.compareTo("eyeColor") == 0) {
            return "Is the character's eye color: " + value + " ?";
        }
        // else
        return "I seem to have forgotten my question";
    }
}

public class Main {

    public static void main(String[] args) throws Exception {


        QueryItem queryItem = new QueryItem("gender");
        queryItem.addValue(false, "Male");

        System.out.println(queryItem);
        Scanner terminal = new Scanner(System.in);
        Scanner scanner = new Scanner(new File("Characters.csv"));
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        arrayList.add("");
        arrayList.add("Female");
        ArrayList<String> newarr = new ArrayList<>();
        newarr.add("Harry James Potter");
        newarr.add("Ronald Bilius Weasley");
        newarr.add("Female");
        JessApi jessApi = new JessApi();
        ArrayList<HarryPotterCharacter> suggestions = jessApi.getPrediction(arrayList);
        if (suggestions.size() > 1) {
            QuestionItem question = generateTables(suggestions);
            System.out.println(question);
            String response = terminal.nextLine();
            if (response.compareTo("y") == 0) {

            } else {

            }
        } else if (suggestions.size() == 1) {
            System.out.println("My guess is that you were thinking of " + suggestions.get(0).getName());
        } else {
            // suggestions.size == 0
            System.out.println("It seems that I don't know about that person");
        }
        // for(String s: jessApi.getPrediction(arrayList))
        //     System.out.println(s);
        // for(String s : jessApi.getPrediction(newarr))
        //     System.out.println(s);
    }

    private static ArrayList<String> getInput() throws IOException, CsvValidationException {
        ArrayList<HashMap<String, Integer>> uniqueAttribMapList = new ArrayList<>();

        File file = new File("Characters.csv");
        CSVReader csvReader = new CSVReader(new FileReader(file));
        String[] arr;
        arr = csvReader.readNext();
        for (int i = 1; i < arr.length - 2; i++) {
            uniqueAttribMapList.add(new HashMap<>());
        }
        while ((arr = csvReader.readNext()) != null) {
            for (int i = 1; i < arr.length - 2; i++) {
                uniqueAttribMapList.get(i).put(arr[i], uniqueAttribMapList.get(i).getOrDefault(arr[i], 0) + 1);
            }
        }
        csvReader = new CSVReader(new FileReader(file));
        arr = csvReader.readNext();
        ArrayList<String> inputFilters = new ArrayList<>();
        for (int i = 2; i < arr.length - 2; i++){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(arr[i]);
            printOptions(uniqueAttribMapList.get(i));
            inputFilters.add(getOptions(Integer.parseInt(br.readLine()),uniqueAttribMapList.get(i)));
        }
        return inputFilters;
    }

    private static String getOptions(int parseInt, HashMap<String, Integer> stringIntegerHashMap) {
        int i = 0;
        for (Map.Entry<String, Integer> m : stringIntegerHashMap.entrySet()){
            if (parseInt == i++){
                return m.getKey();
            }
        }
        return null;
    }

    private static void printOptions(HashMap<String, Integer> stringIntegerHashMap) {
        int i = 0;
        for (Map.Entry<String, Integer> m : stringIntegerHashMap.entrySet()){
            System.out.println(i++ + "> " + m.getKey());
        }
    }

    private static QuestionItem update_maximum (HashMap<String,Integer> table, String key, QuestionItem max, String attribute) {
        if (table.containsKey(key)) {
            if (table.get(key) > max.frequency) {
                int max_freq = table.get(key);
                return new QuestionItem(key, max_freq, attribute);
            }
        }
        // else
        return max;
    }

    private static QuestionItem update_table (HashMap<String,Integer> table, String key, QuestionItem max, String attribute) {
        if (table.containsKey(key)) {
            int value = table.get(key);
            value++;
            table.put(key, value);
        } else {
            table.put(key, 1);
        }
        return update_maximum(table, key, max, attribute);
    }

    private static QuestionItem generateTables (ArrayList<HarryPotterCharacter> characters) {
        QuestionItem max = new QuestionItem("value", Integer.MIN_VALUE, "attribute");
        HashMap<String, Integer> gender_table = new HashMap<String, Integer>();
        HashMap<String, Integer> job_table = new HashMap<String, Integer>();
        HashMap<String, Integer> house_table = new HashMap<String, Integer>();
        HashMap<String, Integer> wand_table = new HashMap<String, Integer>();
        HashMap<String, Integer> patronus_table = new HashMap<String, Integer>();
        HashMap<String, Integer> species_table = new HashMap<String, Integer>();
        HashMap<String, Integer> bloodStatus_table = new HashMap<String, Integer>();
        HashMap<String, Integer> hairColor_table = new HashMap<String, Integer>();
        HashMap<String, Integer> eyeColor_table = new HashMap<String, Integer>();
        for (HarryPotterCharacter character : characters) {
            max = update_table(gender_table, character.getGender(), max, "gender");
            max = update_table(job_table, character.getJob(), max, "job");
            max = update_table(house_table, character.getHouse(), max, "house");
            max = update_table(wand_table, character.getWand(), max, "wand");
            max = update_table(patronus_table, character.getPatronus(), max, "patronus");
            max = update_table(species_table, character.getSpecies(), max, "species");
            max = update_table(bloodStatus_table, character.getBloodStatus(), max, "bloodStatus");
            max = update_table(hairColor_table, character.getHairColor(), max, "hairColor");
            max = update_table(eyeColor_table, character.getEyeColor(), max, "eyeColor");
        }
        return max;
    }

}
