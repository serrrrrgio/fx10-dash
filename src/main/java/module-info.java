module co.edu.uniquindio.fx10 {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.fx10 to javafx.fxml;
    opens co.edu.uniquindio.fx10.controller to javafx.fxml;
    opens co.edu.uniquindio.fx10.model to javafx.base;
    opens co.edu.uniquindio.fx10.repository to javafx.base;
    
    exports co.edu.uniquindio.fx10;
    exports co.edu.uniquindio.fx10.controller;
    exports co.edu.uniquindio.fx10.model;
    exports co.edu.uniquindio.fx10.repository;
}
