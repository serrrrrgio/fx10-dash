package co.edu.uniquindio.fx10.controlador;

import co.edu.uniquindio.fx10.modelo.Producto;
import co.edu.uniquindio.fx10.repositorio.ProductoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListadoProductoController {

    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, String> colCodigo;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, String> colDescripcion;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Producto, Integer> colStock;
    @FXML
    private Button btnEliminar;

    private DashboardController dashboardController;
    private ProductoRepository productoRepository;
    private ObservableList<Producto> listaProductos;

    @FXML
    public void initialize() {
        productoRepository = ProductoRepository.getInstancia();
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrecio.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);
                if (empty || precio == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", precio));
                }
            }
        });
        cargarProductos();
    }

    public void cargarProductos() {
        listaProductos = FXCollections.observableArrayList(productoRepository.getProductos());
        tablaProductos.setItems(listaProductos);
    }

    @FXML
    private void onEliminarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            productoRepository.eliminarProducto(seleccionado);
            cargarProductos();
            mostrarAlerta("Confirmación", "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Debe seleccionar un producto para eliminar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onVolver() {
        dashboardController.restaurarVistaPrincipal();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
    }
}
