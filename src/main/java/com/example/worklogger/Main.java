package com.example.worklogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private Label label;
    private TextField textField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label header = new Label("Hourly Work Logging System");
        header.getStyleClass().add("header");
        header.setFont(Font.font(40));
        header.setPadding(new Insets(20, 0, 20, 0));
        header.setTextFill(Color.BLACK);

        textField = new TextField();
        label = new Label("Hours Worked:");
        Button button = new Button("Save");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(label, textField, button);
        hBox.setSpacing(30);
        hBox.setPadding(new Insets(50, 0, 50, 0));
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(header, hBox);
        vBox.setSpacing(30);
        vBox.setPadding(new Insets(50, 0, 50, 0));
        vBox.setAlignment(Pos.CENTER);

        File file = new File("worklog.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                label.setText("Error creating file");
                label.setTextFill(Color.RED);
                return;
            }
        }

        button.setOnAction(e -> handleButtonClick());
        Scene scene = new Scene(vBox, 1000, 500);
        scene.getStylesheets().add(
                getClass().getResource("style.css").toExternalForm()
        );
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

        try (FileWriter fileWriter = new FileWriter("worklog.txt", true)) {
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
