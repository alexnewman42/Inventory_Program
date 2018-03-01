/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexnewmanc482project;

import alexnewmanc482project.Model.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author anewman
 */
public class MainApp extends Application {
       
    @Override
    public void start(Stage stage) throws Exception {
        Inventory warehouse = new Inventory();
        
        Parent root = FXMLLoader.load(getClass().getResource("View_Controller/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
