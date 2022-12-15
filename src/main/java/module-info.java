module com.example.demo5 {
    requires javafx.controls;
    requires javafx.fxml;


     opens com.example.minirpgfinal to javafx.fxml;
     exports com.example.minirpgfinal;
}