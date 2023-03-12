package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFilter {
    public static void main(String[] args) {

        // specify the input and output file paths
        String inputFilename = "input.csv";
        String outputFilename = "output.csv";

        // specify the rating threshold
        int ratingThreshold = 2500;

        try {
            filterCsvFile(inputFilename, outputFilename, ratingThreshold);
            System.out.println("Filtering successful!");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void filterCsvFile(String inputFilename, String outputFilename, int ratingThreshold) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
                FileWriter writer = new FileWriter(outputFilename)) {

            // create a CSV reader and writer
            String line = reader.readLine();
            String[] headers = line.split(",");
            writer.write(line + System.lineSeparator());

            List<Integer> filterIndices = new ArrayList<>();
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equals("rating") && i >= 3) {
                    filterIndices.add(i);
                    break;
                }
            }

            // iterate over the rows in the input file
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                // check if the rating is greater than the threshold
                boolean shouldFilter = false;
                for (int i = 0; i < filterIndices.size(); i++) {
                    int index = filterIndices.get(i);
                    if (Integer.parseInt(fields[index]) <= ratingThreshold) {
                        shouldFilter = true;
                        break;
                    }
                }

                if (!shouldFilter) {
                    // write the row to the output file
                    writer.write(line + System.lineSeparator());
                }
            }
        }
    }
}
