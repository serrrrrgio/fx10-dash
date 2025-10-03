package co.edu.uniquindio.fx10.controller;

import co.edu.uniquindio.fx10.model.Product;
import co.edu.uniquindio.fx10.repository.ProductRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ProductFormController {

    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtStock;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private ProductRepository productRepository;
    private DashboardController dashboardController;

    @FXML
    public void initialize() {
        productRepository = ProductRepository.getInstance();
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private void onSaveProduct() {
        if (!validateFields()) {
            return;
        }

        try {
            String code = txtCode.getText().trim();
            String name = txtName.getText().trim();
            String description = txtDescription.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            if (productRepository.findByCode(code) != null) {
                showAlert("Error", "A product with that code already exists", Alert.AlertType.ERROR);
                return;
            }

            Product newProduct = new Product(code, name, description, price, stock);
            productRepository.addProduct(newProduct);

            showAlert("Success", "Product created successfully", Alert.AlertType.INFORMATION);

            returnToDashboard();

        } catch (NumberFormatException e) {
            showAlert("Error", "Price and stock must be valid numeric values", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCancel() {
        returnToDashboard();
    }

    private void returnToDashboard() {
        dashboardController.restoreMainView();
    }

    private boolean validateFields() {
        if (txtCode.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Code is required", Alert.AlertType.WARNING);
            return false;
        }
        if (txtName.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Name is required", Alert.AlertType.WARNING);
            return false;
        }
        if (txtDescription.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Description is required", Alert.AlertType.WARNING);
            return false;
        }
        if (txtPrice.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Price is required", Alert.AlertType.WARNING);
            return false;
        }
        if (txtStock.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Stock is required", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
