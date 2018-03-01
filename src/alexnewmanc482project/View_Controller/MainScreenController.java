/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexnewmanc482project.View_Controller;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import alexnewmanc482project.Model.Part;
import alexnewmanc482project.Model.Product;
import alexnewmanc482project.Model.Inventory;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author anewman
 */
public class MainScreenController implements Initializable {
    
    /* class variable for testing add or modify part screens
       and for testing between add or modify product screens 
    */
    public static int boxtype;
    public static int boxtype2;
    
    @FXML
    private Button partSearchButton;
    @FXML
    private TextField partSearchTextField;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partsIDColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partsInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> priceCostPerUnitColumn;
    @FXML
    private Button addPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button productsSearchButton;
    @FXML
    private TextField productSearchTextField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> pricePerUnitColumn;
    @FXML
    private Button addProductButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button exitButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    //class variables
    
    private static Part modifyPart;
    private static Product modifyProduct;
    private static int modifyPartIndex;
    private static int modifyProductIndex;
    
    // accessors
    public static int getModifyProductIndex() {
        return modifyProductIndex;
    }
    
    public static int getModifyPartIndex() {
        return modifyPartIndex;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initializes the part and product tables
        partsIDColumn.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        partsInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
        priceCostPerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        productIDColumn.setCellValueFactory(cellData -> cellData.getValue().getProductIDProperty().asObject());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        productInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
        pricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        setMainApp();
    }    

    @FXML
    private void searchPartsButtonHandler(ActionEvent event) throws IOException {
        String partSearch = partSearchTextField.getText();
        int partIndex = -1;
        if (Inventory.lookupPart(partSearch) == -1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found!");
            alert.setContentText("The search term used does not match any in inventory.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.lookupPart(partSearch);
            Part temp = Inventory.getPartData().get(partIndex);
            ObservableList<Part> tempPartsList = FXCollections.observableArrayList();
            tempPartsList.add(temp);
            partsTable.setItems(tempPartsList);
        }
    }

    @FXML
    private void addPartButtonHandler(ActionEvent event) throws IOException {
        boxtype =1;
        Stage stage; 
        Parent root;
        // get reference to the button's stage         
        stage=(Stage) addPartButton.getScene().getWindow();
        // load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));

        // create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void deletePartButtonHandler(ActionEvent event) throws IOException{
        Part part = partsTable.getSelectionModel().getSelectedItem();
        if (Inventory.validatePartDel(part)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR!");
            alert.setHeaderText("Unable to remove part!");
            alert.setContentText("This part is being used in a product. Remove it first there and then delete it here.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + part.getName() +"?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(part);
                updatePartsTableView();
            }
        }
    }

    @FXML
    private void modifyPartButtonHandler(ActionEvent event) throws IOException {
        boxtype = 2;
        modifyPart = partsTable.getSelectionModel().getSelectedItem();
        modifyPartIndex = Inventory.getPartData().indexOf(modifyPart);
        
        Stage stage; 
        Parent root;
        // get reference to the button's stage         
        stage=(Stage) modifyPartButton.getScene().getWindow();
        // load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("ModifyPartScreen.fxml"));

        // create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void searchProductsButtonHandler(ActionEvent event) throws IOException {
        String searchProd = productSearchTextField.getText();
        int prodIndex = -1;
        if (Inventory.searchForProduct(searchProd) == -1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Product not found");
            alert.setContentText("This product is not found in the inventory.");
            alert.showAndWait();
        } else {
            prodIndex = Inventory.searchForProduct(searchProd);
            Product tempProd = Inventory.getProdInventory().get(prodIndex);
            ObservableList<Product> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(tempProd);
            productsTable.setItems(tempProductList);
        }
    }

    @FXML
    private void addProductButtonHandler(ActionEvent event) throws IOException {
        boxtype2 = 1;
        Stage stage;
        Parent root;
        // get reference to the button's stage
        stage = (Stage) addProductButton.getScene().getWindow();
        // load up other FXML document
        root = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        // create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void deleteProductButtonHandler(ActionEvent event) throws IOException {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm?");
        alert.setContentText("Are you sure you want to delete " + product.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.removeProduct(product);
            updateProductsTableView();
        }
    }

    @FXML
    private void modifyProductButtonHandler(ActionEvent event) throws IOException {
        boxtype2 = 2;
        modifyProduct = productsTable.getSelectionModel().getSelectedItem();
        modifyProductIndex = Inventory.getProdInventory().indexOf(modifyProduct);
        
        Stage stage;
        Parent root;
        // get reference to the button's stage
        stage = (Stage) modifyProductButton.getScene().getWindow();
        // load up other FXML document
        root = FXMLLoader.load(getClass().getResource("ModifyProductScreen.fxml"));
        // create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void exitButtonHandler(ActionEvent event) {
        boolean result = ConfirmBox.display("Exit Program", "Are you sure?");
        if (result == true) {
            System.exit(0);
        }
    }
    
    public void updatePartsTableView() {
        partsTable.setItems(Inventory.getPartData());
    }
    
    public void updateProductsTableView() {
        productsTable.setItems(Inventory.getProdInventory());
    }
    
    public void setMainApp() {
        updatePartsTableView();
        updateProductsTableView();
    }
    
    @FXML
    void clearPartsSearch(ActionEvent event) throws IOException {
        updatePartsTableView();
        partSearchTextField.setText("");
    }
    
    @FXML
    void clearProductsSearch(ActionEvent event) throws IOException {
        updateProductsTableView();
        productSearchTextField.setText("");
    }
    
}
