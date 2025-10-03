package co.edu.uniquindio.fx10.controller;

import co.edu.uniquindio.fx10.model.Product;
import co.edu.uniquindio.fx10.repository.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductListController {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> colCode;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, String> colDescription;
    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    private TableColumn<Product, Integer> colStock;
    @FXML
    private Button btnDelete;

    private DashboardController dashboardController;
    private ProductRepository productRepository;
    private ObservableList<Product> productList;

    @FXML
    public void initialize() {
        productRepository = ProductRepository.getInstance();
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrice.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });
        loadProducts();
    }

    public void loadProducts() {
        productList = FXCollections.observableArrayList(productRepository.getProducts());
        productTable.setItems(productList);
    }

    @FXML
    private void onDeleteProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productRepository.removeProduct(selected);
            loadProducts();
            showAlert("Confirmation", "Product removed successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "You must select a product to delete.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onBack() {
        dashboardController.restoreMainView();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
    }
}
