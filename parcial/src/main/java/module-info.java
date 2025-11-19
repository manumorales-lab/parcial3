module co.edu.poli.parcial {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens co.edu.poli.parcial.controlador to javafx.fxml;
    opens co.edu.poli.parcial.model to javafx.base;
    opens co.edu.poli.parcial.vista to javafx.fxml;
    
    exports co.edu.poli.parcial.controlador;
    exports co.edu.poli.parcial.model;
    exports co.edu.poli.parcial.servicios;
    exports co.edu.poli.parcial.vista;
}