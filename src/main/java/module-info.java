module com.example.biydaalt {
    requires javafx.controls;
    requires javafx.fxml;
    
    exports com.example.biydaalt;
    opens com.example.biydaalt to javafx.fxml;
}