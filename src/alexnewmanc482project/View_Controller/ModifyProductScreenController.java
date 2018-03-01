 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexnewmanc482project.View_Controller;

import alexnewmanc482project.Model.Inventory;
import alexnewmanc482project.Model.Part;
import alexnewmanc482project.Model.Product;
import static alexnewmanc482project.View_Controller.MainScreenController.getModifyProductIndex;
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
public class ModifyProductScreenController implements Initializable {

    //class variables
    private String errorMessage = new String();
    private ObservableList<Part> theseParts = FXCollections.observableArrayList();
    private int productID;
    private int productIndex = getModifyProductIndex();

    
    @FXML
    private TableView<Part> modifyProductAddListTableView;    
    @FXML
    private TableColumn<Part, Integer> modifyProductAddListPartIDColumn;
    @FXML
    private TableColumn<Part, String> modifyProductAddListPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductAddListInvLevelColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductAddListPricePerUnitColumn;
    @FXML
    private TableView<Part> modifyProductDelListTableView;
    @FXML
    private TableColumn<Part, Integer> modifyProductDelListPartIDColumn;
    @FXML
    private TableColumn<Part, String> modifyProductDelListPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductDelListInvLevelColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductDelListPricePerUnitColumn;
    @FXML
    private Button modifyProductSearchButton;
    @FXML
    private Button modifyProductAddButton;
    @FXML
    private Button modifyProductDeleteButton;
    @FXML
    private Button modifyProductSaveButton;
    @FXML
    private Button modifyProductCancelButton;
    @FXML
    private TextField modifyProductSearchTextField;
    @FXML
    private TextField modifyProductIDTextField;
    @FXML
    private TextField modifyProductNameTextField;
    @FXML
    private TextField modifyProductInvTextField;
    @FXML
    private TextField modifyProductPriceTextField;
    @FXML
    private TextField modifyProductMaxTextField;
    @FXML
    private TextField modifyProductMintextField;
    @FXML
    private Label modifyProductLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product product = Inventory.getProdInventory().get(productIndex);
        productID = Inventory.getProdInventory().get(productIndex).getProductID();
        modifyProductIDTextField.setText("AUTO GEN: " + productID);
        modifyProductNameTextField.setText(product.getName());
        modifyProductInvTextField.setText(Integer.toString(product.getInStock()));
        modifyProductPriceTextField.setText(Double.toString(product.getPrice()));
        modifyProductMaxTextField.setText(Integer.toString(product.getMax()));
        modifyProductMintextField.setText(Integer.toString(product.getMin()));
        
        theseParts = product.getAssociatedParts();
        
        modifyProductAddListPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
        modifyProductAddListPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        modifyProductAddListInvLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
        modifyProductAddListPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        
        modifyProductDelListPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
        modifyProductDelListPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        modifyProductDelListInvLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
        modifyProductDelListPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        
        updatePartTableView();
        updateCurrentPartTableView();
    }    

    @FXML
    private void modifyProductSearchButtonHandler(ActionEvent event) {
        String searchPart = modifyProductSearchTextField.getText();
        int partIndex = -1;
        if (Inventory.lookupPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("Search term not found.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.lookupPart(searchPart);
            Part thisPart = Inventory.getPartData().get(partIndex);
            ObservableList<Part> tempProdList = FXCollections.observableArrayList();
            tempProdList.add(thisPart);
            modifyProductAddListTableView.setItems(tempProdList);
        }
    }

    @FXML
    private void modifyProductAddButtonHandler(ActionEvent event) {
        Part part = modifyProductAddListTableView.getSelectionModel().getSelectedItem();
        theseParts.add(part);
        updateCurrentPartTableView();
    }

    @FXML
    private void modifyProductDeleteButtonHandler(ActionEvent event) {
        Part part = modifyProductDelListTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Please Confirm");
        alert.setHeaderText("Confirm Part Delete");
        alert.setContentText("Confirm that you want to delete " + part.getName() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            theseParts.remove(part);
        } else {
            System.out.println("Cancelled.");
        }
    }

    @FXML
    private void modifyProductSaveButtonHandler(ActionEvent event) throws IOException {
        String name = modifyProductNameTextField.getText();
        String inv = modifyProductInvTextField.getText();
        String price = modifyProductPriceTextField.getText();
        String max = modifyProductMaxTextField.getText();
        String min = modifyProductMintextField.getText();
        try {
            errorMessage = Product.isValid(name, Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max), Double.parseDouble(price), theseParts, errorMessage);
            
            if(errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Product");
                alert.setHeaderText("Error!");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            } else {
                Product tempProduct = new Product();
                tempProduct.setProductID(productID);
                tempProduct.setName(name);
                tempProduct.setInStock(Integer.parseInt(inv));
                tempProduct.setMin(Integer.parseInt(min));
                tempProduct.setMax(Integer.parseInt(max));
                tempProduct.setPrice(Double.parseDouble(price));
                tempProduct.setAssociatedParts(theseParts);
                Inventory.updateProduct(productIndex, tempProduct);
                
                Stage stage;
                Parent root;
                stage = (Stage) modifyProductSaveButton.getScene().getWindow();
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
    private void modifyProductCancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confrimation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel update of " + modifyProductNameTextField.getText() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            // get reference to the buttone's stage
            stage = (Stage) modifyProductCancelButton.getScene().getWindow();
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
        modifyProductDelListTableView.setItems(theseParts);
    }
    
    public void updatePartTableView() {
        modifyProductAddListTableView.setItems(Inventory.getPartData());
    }
    
    @FXML
    void clearPartsSearch(ActionEvent e) throws IOException {
        updatePartTableView();
        modifyProductSearchTextField.setText("");
    }
    
}
