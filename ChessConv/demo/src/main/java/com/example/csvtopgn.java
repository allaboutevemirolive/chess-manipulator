package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class csvtopgn {
    public void convertCsvToPgn(String inputFilename, String outputFilename) throws IOException {
        StringBuilder pgn = new StringBuilder();
        String puzzleId, fen, moves, rating, ratingDeviation, popularity, nbPlays,
                themes, gameUrl, openingFamily, openingVariation;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 11) {
                    continue;
                }
                puzzleId = fields[0];
                fen = fields[1];
                moves = fields[2];
                rating = fields[3];
                ratingDeviation = fields[4];
                popularity = fields[5];
                nbPlays = fields[6];
                themes = fields[7];
                gameUrl = fields[8];
                openingFamily = fields[9];
                openingVariation = fields[10];

                pgn.append(String.format("[Event \"%s\"]%n", puzzleId))
                        .append(String.format("[FEN \"%s\"]%n", fen))
                        .append(String.format("[Site \"%s\"]%n", gameUrl))
                        .append(String.format("[White \"%s\"]%n", rating))
                        .append(String.format("[WhiteElo \"%s\"]%n", ratingDeviation))
                        .append(String.format("[Popularity \"%s\"]%n", popularity))
                        .append(String.format("[BlackElo \"%s\"]%n", nbPlays))
                        .append(String.format("[Black \"%s\"]%n", themes))
                        .append(String.format("[OpeningFamily \"%s\"]%n", openingFamily))
                        .append(String.format("[OpeningVariation \"%s\"]%n", openingVariation))
                        .append(String.format("%s 1-0%n%n", moves));
            }
        }

        try (FileWriter writer = new FileWriter(outputFilename)) {
            writer.write(pgn.toString());
        }
    }
}