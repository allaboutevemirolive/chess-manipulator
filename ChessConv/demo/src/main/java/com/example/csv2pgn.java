package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class csv2pgn {
    public void convertCsvToPgn(String inputFilename, String outputFilename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 11) {
                    continue;
                }
                String puzzleId = fields[0];
                String fen = fields[1];
                String moves = fields[2];
                String rating = fields[3];
                String ratingDeviation = fields[4];
                String popularity = fields[5];
                String nbPlays = fields[6];
                String themes = fields[7];
                String gameUrl = fields[8];
                String openingFamily = fields[9];
                String openingVariation = fields[10];

                writer.write(String.format("[Event \"%s\"]%n", puzzleId));
                writer.write(String.format("[FEN \"%s\"]%n", fen));
                writer.write(String.format("[Site \"%s\"]%n", gameUrl));
                writer.write(String.format("[White \"%s\"]%n", rating));
                writer.write(String.format("[WhiteElo \"%s\"]%n", ratingDeviation));
                writer.write(String.format("[Popularity \"%s\"]%n", popularity));
                writer.write(String.format("[BlackElo \"%s\"]%n", nbPlays));
                writer.write(String.format("[Black \"%s\"]%n", themes));
                writer.write(String.format("[OpeningFamily \"%s\"]%n", openingFamily));
                writer.write(String.format("[OpeningVariation \"%s\"]%n", openingVariation));
                writer.write(String.format("%s 1-0%n%n", moves));
            }
        }
    }
}