package ChessConv.demo.src.main.java;

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

    private final csv2pgn obj = new csv2pgn();
    private final Label statusLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        Button selectButton = new Button("Select CSV File");
        selectButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                statusLabel.setText("File selected: " + selectedFile.getAbsolutePath());
            }
        });

        Button convertButton = new Button("Convert File");
        convertButton.setOnAction(e -> {
            String selectedFile = statusLabel.getText().replace("File selected: ", "");
            if (!selectedFile.isEmpty()) {
                convertCsvToPgn(selectedFile);
            } else {
                statusLabel.setText("Please select a file first.");
            }
        });

        Button infoButton = new Button("Info");
        infoButton.setOnAction(e -> {
            showInfoPopup();
        });

        statusLabel.setPrefWidth(400);
        statusLabel.setAlignment(Pos.CENTER);

        HBox buttonsBox = new HBox(10, selectButton, convertButton, infoButton);
        buttonsBox.setAlignment(Pos.CENTER);

        StackPane statusPane = new StackPane(statusLabel);

        root.setCenter(buttonsBox);
        root.setBottom(statusPane);

        Scene scene = new Scene(root, 500, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void convertCsvToPgn(String inputFilename) {
        String outputFilename = "output.pgn";

        new Thread(() -> {
            long startTime = System.nanoTime();

            try {
                obj.convertCsvToPgn(inputFilename, outputFilename);
                Platform.runLater(() -> {
                    long executionTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                    statusLabel.setText("Conversion successful! Execution time: " + executionTime + "ms");
                });
            } catch (IOException ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    statusLabel.setText("Error: " + ex.getMessage());
                });
            }
        }).start();
    }

    private void showInfoPopup() {
        Label authorLabel = new Label("Author: allaboutevemirolive");
        Label versionLabel = new Label("Version: 1.0");
        Label rightsLabel = new Label("Copyright 2023, allaboutevemirolive, All rights reserved.");

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
