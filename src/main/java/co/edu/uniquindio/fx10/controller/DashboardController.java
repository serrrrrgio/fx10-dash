package co.edu.uniquindio.fx10.controller;

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
    private VBox mainContainer;
    @FXML
    private Label lblTitle;
    @FXML
    private VBox viewContainer;
    @FXML
    private Button btnViewProductList;
    @FXML
    private Button btnCreateProduct;

    private Parent mainView;

    @FXML
    public void initialize() {
        if (!viewContainer.getChildren().isEmpty()) {
            mainView = (Parent) viewContainer.getChildren().get(0);
        }
    }

    @FXML
    private void onViewProductList() {
        loadView("/co/edu/uniquindio/fx10/view/ProductList.fxml");
    }

    @FXML
    private void onCreateProduct() {
        loadView("/co/edu/uniquindio/fx10/view/ProductForm.fxml");
    }

    public void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
            Parent view = loader.load();

            Object controller = loader.getController();
            if (controller instanceof ProductListController) {
                ((ProductListController) controller).setDashboardController(this);
            } else if (controller instanceof ProductFormController) {
                ((ProductFormController) controller).setDashboardController(this);
            }

            viewContainer.getChildren().setAll(view);
        } catch (IOException e) {
            showAlert("Loading Error", "Could not load the view: " + fxml, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void restoreMainView() {
        if (mainView != null) {
            viewContainer.getChildren().setAll(mainView);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
