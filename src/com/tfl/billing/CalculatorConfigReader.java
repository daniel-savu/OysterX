package com.tfl.billing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CalculatorConfigReader {
    private final String csvFile = "CalculatorConfig.csv";
    private HashMap<String,String> rawConstants = new HashMap<>();

    public CalculatorConfigReader() {
        readConfigFile();
    }

    private void readConfigFile() {
        FileReader fileReader = openConfigFile();
        BufferedReader br = new BufferedReader(fileReader);
        String cvsSeparator = ",";
        String csvLine;
        try {
            while ((csvLine = br.readLine()) != null) {
                String[] configLine = csvLine.split(cvsSeparator);
                addRowValuesToRawConstants(configLine);
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

    void addRowValuesToRawConstants(String[] configLine) {
        String constantName = configLine[0];
        String constantValue = configLine[1];
        rawConstants.put(constantName, constantValue);
    }

    HashMap<String,String> getRawConstants() {
        return rawConstants;
    }
}
