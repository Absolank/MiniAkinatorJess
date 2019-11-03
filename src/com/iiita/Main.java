package com.iiita;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
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
        for(String s: jessApi.getPrediction(arrayList))
            System.out.println(s);
        for(String s : jessApi.getPrediction(newarr))
            System.out.println(s);
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

}
