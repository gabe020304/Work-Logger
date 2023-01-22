package com.example.worklogger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Label label;

    @Override
    public void start(Stage primaryStage) {
        // Create UI elements
        TextField textField = new TextField();
        label = new Label("Work done:");
        Button button = new Button("Save");

        // Create a HBox layout
        HBox hBox = new HBox();
        hBox.getChildren().addAll(textField, label, button);

        // Add event handler to the button
        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                // Get the data entered by the user
                String data = textField.getText();

                // Validate the data
                if (data.isEmpty()) {
                    // Show an error message if the data is empty
                    label.setText("Please enter a value");
                    label.setTextFill(Color.RED);
                } else {
                    // Check if the input can be parsed to an integer
                    try {
                        int input = Integer.parseInt(data);
                    } catch (NumberFormatException e) {
                        label.setText("Please enter a valid number");
                        label.setTextFill(Color.RED);
                        return;
                    }

                    // Save the data if it is valid
                    try {
                        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/Denver"));
                        String dateTimeString = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        FileWriter fileWriter = new FileWriter("worklog.txt", true);
                        fileWriter.write(dateTimeString + " - " + input + "\n");
                        fileWriter.close();
                        label.setText("Data saved");
                    } catch (IOException e) {
                        label.setText("Error saving data");
                        label.setTextFill(Color.RED);
                    }
                }
            }
        });

        // Create a scene and set it to the primary stage
        Scene scene = new Scene(hBox, 300, 50);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
