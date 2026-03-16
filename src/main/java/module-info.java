module com.example.weatherdataapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.weatherdataapplication to javafx.fxml;
    exports com.example.weatherdataapplication;
}