package co.edu.uniquindio.fx10.controlador;

import co.edu.uniquindio.fx10.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardController {

    @FXML
    private VBox contenedorPrincipal;
    @FXML
    private Label lblTitulo;
    @FXML
    private VBox contenedorVistas;
    @FXML
    private Button btnVerListado;
    @FXML
    private Button btnCrearProducto;

    private Parent vistaPrincipal;

    @FXML
    public void initialize() {
        if (!contenedorVistas.getChildren().isEmpty()) {
            vistaPrincipal = (Parent) contenedorVistas.getChildren().get(0);
        }
    }

    @FXML
    private void onVerListado() {
        loadView("/co/edu/uniquindio/fx10/vista/ListadoProducto.fxml");
    }

    @FXML
    private void onCrearProducto() {
        loadView("/co/edu/uniquindio/fx10/vista/FormularioProducto.fxml");
    }

    public void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
            Parent vista = loader.load();

            Object controller = loader.getController();
            if (controller instanceof ListadoProductoController) {
                ((ListadoProductoController) controller).setDashboardController(this);
            } else if (controller instanceof FormularioProductoController) {
                ((FormularioProductoController) controller).setDashboardController(this);
            }

            contenedorVistas.getChildren().setAll(vista);
        } catch (IOException e) {
            mostrarAlerta("Error de Carga", "No se pudo cargar la vista: " + fxml, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void restaurarVistaPrincipal() {
        if (vistaPrincipal != null) {
            contenedorVistas.getChildren().setAll(vistaPrincipal);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
