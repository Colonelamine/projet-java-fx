module com.example.patientgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;
    requires json.simple;


    opens com.example.patientgui to javafx.fxml;
    exports com.example.patientgui;
}