package com.example;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    // Creating an object of csv2pgn class
    private final csv2pgn obj = new csv2pgn();
    // Status label for showing messages
    private final Label statusLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        // Root pane of BorderPane
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Select Button for choosing CSV file from File Chooser
        Button selectButton = new Button("Select CSV File");
        selectButton.setOnAction(e -> {
            // Choose CSV file from file chooser and set the selected file name to status label
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                statusLabel.setText("File selected: " + selectedFile.getAbsolutePath());
            }
        });

        // Convert Button for converting CSV file into PGN format
        Button convertButton = new Button("Convert File");
        convertButton.setOnAction(e -> {
            // Get the selected file name and call the function to convert the file
            String selectedFile = statusLabel.getText().replace("File selected: ", "");
            if (!selectedFile.isEmpty()) {
                convertCsvToPgn(selectedFile);
            } else {
                statusLabel.setText("Please select a file first.");
            }
        });

        // Info button for displaying author information
        Button infoButton = new Button("Info");
        infoButton.setOnAction(e -> {
            showInfoPopup();
        });

        // Set width and alignment of status label
        statusLabel.setPrefWidth(400);
        statusLabel.setAlignment(Pos.CENTER);

        // Create a HBox to store buttons
        HBox buttonsBox = new HBox(10, selectButton, convertButton, infoButton);
        buttonsBox.setAlignment(Pos.CENTER);

        // Create a StackPane to display message/status
        StackPane statusPane = new StackPane(statusLabel);

        // Add buttons to center and status pane to bottom of BorderPane
        root.setCenter(buttonsBox);
        root.setBottom(statusPane);

        Scene scene = new Scene(root, 500, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Function for converting CSV file into PGN format
    private void convertCsvToPgn(String inputFilename) {
        // Output filename with .pgn extension
        String outputFilename = "output.pgn";

        // Run conversion process in thread
        new Thread(() -> {
            long startTime = System.nanoTime();

            try {
                obj.convertCsvToPgn(inputFilename, outputFilename);
                // Show success message after execution
                Platform.runLater(() -> {
                    long executionTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                    statusLabel.setText("Conversion successful! Execution time: " + executionTime + "ms");
                });
            } catch (IOException ex) {
                // If error occurs during conversion, show error message
                ex.printStackTrace();
                Platform.runLater(() -> {
                    statusLabel.setText("Error: " + ex.getMessage());
                });
            }
        }).start();
    }

    // Function for displaying information popup about the author
    private void showInfoPopup() {
        // Information labels
        Label authorLabel = new Label("Author: allaboutevemirolive");
        Label versionLabel = new Label("Version: 1.0");
        Label rightsLabel = new Label("Copyright 2023, allaboutevemirolive, All rights reserved.");

        // Hyperlinks to author's social media accounts
        Hyperlink twitterLink = new Hyperlink("Twitter");
        twitterLink.setOnAction(e -> {
            getHostServices().showDocument("https://twitter.com/akmalfirdxus");
        });

        Hyperlink githubLink = new Hyperlink("GitHub");
        githubLink.setOnAction(e -> {
            getHostServices().showDocument("https://github.com/allaboutevemirolive");
        });

        Hyperlink instagramLink = new Hyperlink("Instagram");
        instagramLink.setOnAction(e -> {
            getHostServices().showDocument("https://www.instagram.com/akmxlfirdaus/");
        });

        HBox linksBox = new HBox(10, twitterLink, githubLink, instagramLink);
        linksBox.setAlignment(Pos.CENTER);

        VBox infoBox = new VBox(10, authorLabel, versionLabel, rightsLabel, linksBox);
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setPadding(new Insets(10));

        Scene infoScene = new Scene(infoBox, 300, 150);

        Stage infoStage = new Stage();
        infoStage.setScene(infoScene);
        infoStage.setTitle("Info");
        infoStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
