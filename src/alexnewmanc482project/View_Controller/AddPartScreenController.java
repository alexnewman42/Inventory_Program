/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexnewmanc482project.View_Controller;

import alexnewmanc482project.Model.Inhouse;
import alexnewmanc482project.Model.Outsourced;
import alexnewmanc482project.Model.Inventory;
import alexnewmanc482project.Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author anewman
 */
public class AddPartScreenController implements Initializable {

    //class variables
    private String errorMessage = new String();
    private int partID;
    private boolean isOutsourced;
    
    @FXML
    private Label addPart;
    @FXML
    private TextField addPartIDTextField;
    @FXML
    private TextField addPartNameTextField;
    @FXML
    private TextField addPartInvTextField;
    @FXML
    private TextField addPartPriceTextField;
    @FXML
    private TextField addPartMaxTextField;
    @FXML
    private TextField addPartMinTextField;
    @FXML
    private Label addPartDynamicLabel;
    @FXML
    private TextField addPartDynamicField;
    @FXML
    private RadioButton addPartInhouseRadioButton;
    @FXML
    private ToggleGroup inout;
    @FXML
    private RadioButton addPartOutsourcedRadioButton;
    @FXML
    private Button addPartSaveButton;
    @FXML
    private Button addPartCancelButton;

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partID = Inventory.getPartCount();
        addPartIDTextField.setText("Auto Gen: " + partID);
    }    

    @FXML
    private void addPartInHouseRadioButtonHandler(ActionEvent event) {
        addPartDynamicLabel.setText("Machine ID");
        addPartDynamicField.setPromptText("Machine ID");
        isOutsourced = false;
    }

    @FXML
    private void addPartOutsourcedRadioButtonHandler(ActionEvent event) {
        addPartDynamicLabel.setText("Company Name");
        addPartDynamicField.setPromptText("Company Name");
        isOutsourced = true;
    }

    @FXML
    private void addPartSaveButtonHandler(ActionEvent event) throws IOException {
        String partName = addPartNameTextField.getText();
        String partInv = addPartInvTextField.getText();
        String partPrice = addPartPriceTextField.getText();
        String partMax = addPartMaxTextField.getText();
        String partMin = addPartMinTextField.getText();
        String partDynamic = addPartDynamicField.getText(); 
        
        try {
            errorMessage = Part.isValidPart(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), errorMessage);
            if (errorMessage.length() > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Error");
                alert.setTitle("Error While Adding Part");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {
                // check if outsourced or inhouse is selected and then add part
                if (isOutsourced == false) {
                    Inhouse inhousePart = new Inhouse();
                    inhousePart.setPartID(partID);
                    inhousePart.setName(partName);
                    inhousePart.setInStock(Integer.parseInt(partInv));
                    inhousePart.setPrice(Double.parseDouble(partPrice));
                    inhousePart.setMax(Integer.parseInt(partMax));
                    inhousePart.setMin(Integer.parseInt(partMin));
                    inhousePart.setMachineID(Integer.parseInt(partDynamic));
                    Inventory.addPart(inhousePart);
                } else {
                    Outsourced outPart = new Outsourced();
                    outPart.setPartID(partID);
                    outPart.setName(partName);
                    outPart.setInStock(Integer.parseInt(partInv));
                    outPart.setPrice(Double.parseDouble(partPrice));
                    outPart.setMax(Integer.parseInt(partMax));
                    outPart.setMin(Integer.parseInt(partMin));
                    outPart.setCompanyName(partDynamic);
                    Inventory.addPart(outPart);
                }
                Stage stage;
                Parent root;
                stage = (Stage) addPartSaveButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setTitle("Error While Adding Part");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }

    @FXML
    private void addPartCancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confrimation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel update of " + addPartNameTextField.getText() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            // get reference to the buttone's stage
            stage = (Stage) addPartCancelButton.getScene().getWindow();
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
    
}
