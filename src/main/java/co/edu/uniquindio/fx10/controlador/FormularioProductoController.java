package co.edu.uniquindio.fx10.controlador;

import co.edu.uniquindio.fx10.modelo.Producto;
import co.edu.uniquindio.fx10.repositorio.ProductoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FormularioProductoController {

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtStock;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private ProductoRepository productoRepository;
    private DashboardController dashboardController;

    @FXML
    public void initialize() {
        productoRepository = ProductoRepository.getInstancia();
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private void onGuardarProducto() {
        if (!validarCampos()) {
            return;
        }

        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            if (productoRepository.buscarPorCodigo(codigo) != null) {
                mostrarAlerta("Error", "Ya existe un producto con ese código", Alert.AlertType.ERROR);
                return;
            }

            Producto nuevoProducto = new Producto(codigo, nombre, descripcion, precio, stock);
            productoRepository.agregarProducto(nuevoProducto);

            mostrarAlerta("Éxito", "Producto creado correctamente", Alert.AlertType.INFORMATION);

            volverAlDashboard();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio y stock deben ser valores numéricos válidos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCancelar() {
        volverAlDashboard();
    }

    private void volverAlDashboard() {
        dashboardController.restaurarVistaPrincipal();
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El código es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El nombre es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtDescripcion.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "La descripción es obligatoria", Alert.AlertType.WARNING);
            return false;
        }
        if (txtPrecio.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El precio es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtStock.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El stock es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
