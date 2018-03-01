/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexnewmanc482project.View_Controller;

import alexnewmanc482project.Model.Inventory;
import static alexnewmanc482project.Model.Inventory.getPartData;
import alexnewmanc482project.Model.Part;
import alexnewmanc482project.Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author anewman
 */
public class AddProductScreenController implements Initializable {
    
    //class variables
    private int productID;
    private String errorMessage = new String();
    private ObservableList<Part> theseParts = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Part> addProductAddTableView;
    @FXML
    private TableColumn<Part, Integer> addProductAddListPartIDColumn;
    @FXML
    private TableColumn<Part, String> addProductAddListPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> addProductAddListInvLevelColumn;
    @FXML
    private TableColumn<Part, Double> addProductAddListPricePerUnitColumn;
    @FXML
    private TableView<Part> addProductDelTableView;
    @FXML
    private TableColumn<Part, Integer> addProductDelListPartIDColumn;
    @FXML
    private TableColumn<Part, String> addProductDelListPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> addProductDelListInvLevelColumn;
    @FXML
    private TableColumn<Part, Double> addProductDelListPricePerUnitColumn;
    @FXML
    private Button addProductSearchButton;
    @FXML
    private Button addProductAddButton;
    @FXML
    private Button addProductDeleteButton;
    @FXML
    private Button addProductSaveButton;
    @FXML
    private Button addProductCancelButton;
    @FXML
    private TextField addProductSearchTextField;
    @FXML
    private TextField addProductIDTextField;
    @FXML
    private TextField addProductNameTextField;
    @FXML
    private TextField addProductInvoiceTextField;
    @FXML
    private TextField addProductPriceTextField;
    @FXML
    private TextField addProductMaxTextField;
    @FXML
    private TextField addProductMintextField;
    @FXML
    private Label AddProductLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addProductAddListPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
        addProductAddListPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        addProductAddListInvLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
        addProductAddListPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        
        addProductDelListPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
        addProductDelListPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        addProductDelListInvLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
        addProductDelListPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        
        updatePartTableView();
        updateCurrentPartTableView();
        
        productID = Inventory.getProdCount();
        addProductIDTextField.setText("AUTO GEN: " + productID);
    }    

    public AddProductScreenController(){
        
    }
    
    @FXML
    private void addProductSearchButtonHandler(ActionEvent event) {
        int partIndex = -1;
        String partSearch = addProductSearchTextField.getText();
        
        if (Inventory.lookupPart(partSearch) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("The part you are searching for cannot be found.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.lookupPart(partSearch);
            Part tempPart = Inventory.getPartData().get(partIndex);
            
            ObservableList<Part> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(tempPart);
            
            addProductAddTableView.setItems(tempProductList);
        }
    }

    @FXML
    private void addProductAddButtonHandler(ActionEvent event) {
        Part part = addProductAddTableView.getSelectionModel().getSelectedItem();
        theseParts.add(part);
        updateCurrentPartTableView();
    }

    @FXML
    private void addProductDeleteButtonHandler(ActionEvent event) {
        Part part = addProductDelTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm part delete.");
        alert.setContentText("Confirm that you want to delete " + part.getName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            theseParts.remove(part);
            System.out.println("Deleted part.");
        } else {
            System.out.println("Delete canceled.");
        }
    }

    @FXML
    private void addProductSaveButtonHandler(ActionEvent event) throws IOException {
        String productName = addProductNameTextField.getText();
        String productInv = addProductInvoiceTextField.getText();
        String productPrice = addProductPriceTextField.getText();
        String productMax = addProductMaxTextField.getText();
        String productMin = addProductMintextField.getText();
        
        try {
            errorMessage = Product.isValid(productName, Integer.parseInt(productInv), Integer.parseInt(productMin), Integer.parseInt(productMax), Double.parseDouble(productPrice), theseParts, errorMessage);
            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error");
                alert.setTitle("Error! Invalid Product!");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {
                System.out.println("Product name: " + productName);
                Product tempProduct = new Product();
                tempProduct.setProductID(productID);
                tempProduct.setName(productName);
                tempProduct.setPrice(Double.parseDouble(productPrice));
                tempProduct.setInStock(Integer.parseInt(productInv));
                tempProduct.setMin(Integer.parseInt(productMin));
                tempProduct.setMax(Integer.parseInt(productMax));
                tempProduct.setAssociatedParts(theseParts);
                Inventory.addProduct(tempProduct);
                
                Stage stage;
                Parent root;
                stage = (Stage) addProductSaveButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error adding part!");
            alert.setHeaderText("Error!");
            alert.setContentText("No fields can be left blank.");
            alert.showAndWait();
        }
        
        
    }

    @FXML
    private void addProductCancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confrimation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel creation of " + addProductNameTextField.getText() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            // get reference to the buttone's stage
            stage = (Stage) addProductCancelButton.getScene().getWindow();
            // load up other FXML document
            root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            // create a new scene with root and set the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("User canceled cancel.");
        }

    }
    
    public void updateCurrentPartTableView() {
        addProductDelTableView.setItems(theseParts);
    }
    
    public void updatePartTableView() {
        addProductAddTableView.setItems(getPartData());
    }
    
    @FXML
    void clearPartsSearch (ActionEvent event) throws IOException {
        updatePartTableView();
        addProductSearchTextField.setText("");
    }
    
    
}
