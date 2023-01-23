module com.example.worklogger {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.worklogger to javafx.fxml;
    exports com.example.worklogger;
}