package com.example.worklogger;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private Label label;
    private TextField textField;
    private FileWriter fileWriter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        textField = new TextField();
        label = new Label("Work done:");
        Button button = new Button("Save");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(textField, label, button);

        File file = new File("worklog.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                label.setText("Error creating file");
                label.setTextFill(Color.RED);
                return;
            } finally {
                try {
                    if (file != null) {
                        file.close();
                    }
                } catch (IOException e) {
                    label.setText("Error closing file");
                    label.setTextFill(Color.RED);
                }
            }
        }

        button.setOnAction(e -> handleButtonClick());
        Scene scene = new Scene(hBox, 300, 50);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    private void handleButtonClick() {
        String data = textField.getText();
        if (data.isEmpty()) {
            label.setText("Please enter a value");
            label.setTextFill(Color.RED);
            return;
        }
        if (!isNumeric(data)) {
            label.setText("Please enter a valid number");
            label.setTextFill(Color.RED);
            return;
        }
        int input = Integer.parseInt(data);
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/Denver"));
        String dateTimeString = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        StringBuilder sb = new StringBuilder();
        sb.append(dateTimeString).append(" - ").append(input).append("\n");

        try(FileWriter fileWriter = new FileWriter("worklog.txt", true)){
            fileWriter.write(sb.toString());
        } catch (IOException e) {
            label.setText("Error saving data");
            label.setTextFill(Color.RED);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}