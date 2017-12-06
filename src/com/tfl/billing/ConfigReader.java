package com.tfl.billing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ConfigReader {
    private final String csvFile = "Config.csv";
    private HashMap<String,String> rawConstants = new HashMap<>();

    public ConfigReader() {
        readConfigFile();
    }

    private void readConfigFile() {
        FileReader fileReader = openConfigFile();
        BufferedReader br = new BufferedReader(fileReader);
        String cvsSeparator = ",";
        String csvRow;
        try {
            while ((csvRow = br.readLine()) != null) {
                String[] configLine = csvRow.split(cvsSeparator);
                putRowValuesInHashMap(configLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileReader openConfigFile() {
        try {
            FileReader fileReader = new FileReader(csvFile);
            return fileReader;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    void putRowValuesInHashMap(String[] configLine) {
        if (configLine.length > 1) {
            String constantName = configLine[0].trim().replaceAll("\uFEFF", "");
            String constantValue = configLine[1].trim().replaceAll("\uFEFF", "");
            rawConstants.put(constantName, constantValue);
        }
    }

    HashMap<String,String> getRawConstants() {
        return rawConstants;
    }
}
