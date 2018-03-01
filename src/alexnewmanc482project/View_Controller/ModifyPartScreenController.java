/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexnewmanc482project.View_Controller;

import alexnewmanc482project.Model.Inhouse;
import alexnewmanc482project.Model.Inventory;
import static alexnewmanc482project.Model.Inventory.getPartData;
import alexnewmanc482project.Model.Outsourced;
import alexnewmanc482project.Model.Part;
import static alexnewmanc482project.View_Controller.MainScreenController.getModifyPartIndex;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
public class ModifyPartScreenController implements Initializable {

    //class variables
    private int partID;
    int partIndex = getModifyPartIndex();
    private boolean isOutsourced;
    private String errorMessage = new String();
    
    @FXML
    private Label modifyPart;
    @FXML
    private TextField modifyPartIDTextField;
    @FXML
    private TextField modifyPartNameTextField;
    @FXML
    private TextField modifyPartInvTextField;
    @FXML
    private TextField modifyPartPriceTextField;
    @FXML
    private TextField modifyPartMaxTextField;
    @FXML
    private TextField modifyPartMinTextField;
    @FXML
    private TextField modifyPartDynamicTextField;
    @FXML
    private Label modifyPartDynamicLabel;
    @FXML
    private RadioButton modifyPartInhouseRadioButton;
    @FXML
    private ToggleGroup inout;
    @FXML
    private RadioButton modifyPartOutsourcedRadioButton;
    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private Button modifyPartCancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part part = Inventory.getPartData().get(partIndex);
        partID = part.getPartID();
        modifyPartIDTextField.setText("Part ID Autoset to: " + partID);
        modifyPartNameTextField.setText(part.getName());
        modifyPartInvTextField.setText(Integer.toString(part.getInStock()));
        modifyPartPriceTextField.setText(Double.toString(part.getPrice()));
        modifyPartMaxTextField.setText(Integer.toString(part.getMax()));
        modifyPartMinTextField.setText(Integer.toString(part.getMin()));
        
        if (part instanceof Inhouse) {
            modifyPartDynamicTextField.setText(Integer.toString(((Inhouse)getPartData().get(partIndex)).getMachineID()));
            modifyPartDynamicLabel.setText("Machine ID");
            modifyPartInhouseRadioButton.setSelected(true);
            isOutsourced = false;
        } else {
            modifyPartDynamicTextField.setText(((Outsourced)getPartData().get(partIndex)).getCompanyName());
            modifyPartDynamicLabel.setText("Company Name");
            modifyPartOutsourcedRadioButton.setSelected(true);
            isOutsourced = true;
        }
    }    

    @FXML
    private void modifyPartInHouseRadioButtonHandler(ActionEvent event) {
        isOutsourced = false;
        modifyPartDynamicLabel.setText("Machine ID");
        modifyPartDynamicTextField.setPromptText("Machine ID");
    }

    @FXML
    private void modifyPartOutsourcedRadioButtonHandler(ActionEvent event) {
        isOutsourced = true;
        modifyPartDynamicLabel.setText("Company Name");
        modifyPartDynamicTextField.setPromptText("Company Name");
    }

    @FXML
    private void modifyPartSaveButtonHandler(ActionEvent event) throws IOException {
        String partName = modifyPartNameTextField.getText();
        String partInv = modifyPartInvTextField.getText();
        String partPrice = modifyPartPriceTextField.getText();
        String partMax = modifyPartMaxTextField.getText();
        String partMin = modifyPartMinTextField.getText();
        String partDynamic = modifyPartDynamicTextField.getText();
        
        try {
            errorMessage = Part.isValidPart(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), errorMessage);
            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error");
                alert.setTitle("Error While Adding Part");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {
                if (isOutsourced == false) {
                    Inhouse inhousePart = new Inhouse();
                    inhousePart.setPartID(partID);
                    inhousePart.setName(partName);
                    inhousePart.setInStock(Integer.parseInt(partInv));
                    inhousePart.setPrice(Double.parseDouble(partPrice));
                    inhousePart.setMax(Integer.parseInt(partMax));
                    inhousePart.setMin(Integer.parseInt(partMin));
                    inhousePart.setMachineID(Integer.parseInt(partDynamic));
                    Inventory.updatePart(partIndex, inhousePart);
                } else {
                    Outsourced outPart = new Outsourced();
                    outPart.setPartID(partID);
                    outPart.setName(partName);
                    outPart.setInStock(Integer.parseInt(partInv));
                    outPart.setPrice(Double.parseDouble(partPrice));
                    outPart.setMax(Integer.parseInt(partMax));
                    outPart.setMin(Integer.parseInt(partMin));
                    outPart.setCompanyName(partDynamic);
                    Inventory.updatePart(partIndex, outPart);
                }
                Stage stage;
                Parent root;
                stage = (Stage) modifyPartSaveButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setTitle("Error While Adding Part");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }

    @FXML
    private void modifyPartCancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confrimation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel update of " + modifyPartNameTextField.getText() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            // get reference to the buttone's stage
            stage = (Stage) modifyPartCancelButton.getScene().getWindow();
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
