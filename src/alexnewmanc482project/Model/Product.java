/*
 * Contains information for products
 */
package alexnewmanc482project.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author anewman
 */
public class Product {
    // class variables
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private final IntegerProperty productID, min, max, inStock;
    private final StringProperty name;
    private final DoubleProperty price;
    
    // constructors
    public Product() {
        productID = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
        inStock = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
     }
    
    // accessor methods
    public ObservableList getAssociatedParts(){
        return associatedParts;
    }
    
    public StringProperty getNameProperty(){
        return name;
    }
    
    public IntegerProperty getProductIDProperty() {
        return productID;
    }
    
    public DoubleProperty getPriceProperty(){
        return price;
    }
    
    public IntegerProperty getInStockProperty() {
        return inStock;
    }
    
    public String getName(){
        return this.name.get();
    }
    
    public double getPrice(){
        return this.price.get();
    }
    
    public int getInStock(){
        return this.inStock.get();
    } 
    
    public int getMin(){
        return this.min.get();
    }
    
    public int getMax(){
        return this.max.get();
    }
    
    public int getProductID(){
        return this.productID.get();
    }
        
    // mutator methods
    public void setAssociatedParts(ObservableList<Part> parts){
        associatedParts = parts;
    }
    
    public void setName(String name){
        this.name.set(name);
    }
    
    public void setPrice(double price){
        this.price.set(price);
    }
    
    public void setInStock(int inStock){
        this.inStock.set(inStock);
    }
    
    public void setMin(int min){
        this.min.set(min);
    }
    
    public void setMax(int max){
        this.max.set(max);
    }
    
    public void setProductID(int productID){
        this.productID.set(productID);
    }
    
    // class methods
    public void addAssociatedPart(Part prt){
        associatedParts.add(prt);
    }
    
    public boolean removeAssociatedPart(int index){
        return associatedParts.remove(lookupAssociatedPart(index));
    }
    
    public Part lookupAssociatedPart(int index) {
        return associatedParts.get(index);
    }
    
    // validation method for products
    public static String isValid(String name, int inv, int min, int max, double price, ObservableList<Part> parts, String error) {
        double sum = 0.00;
        
        for (int i =0; i < parts.size(); i++) {
            sum += parts.get(i).getPrice();
        }
        
        if (name.equals("")) {
            error += ("The product must have a name.");
        }
        
        if (min > max) {
            error += ("The inventory minimum must be less than the maximum.");
        }
        
        if (min < 0) {
            error += ("The inventory must be greater than zero.");
        }
        
        if (inv < min || inv > max) {
            error += ("The inventory must be between the minimum and the maximum.");
        }
        
        if (price < 0) {
            error += ("The product price must be greater than zero.");
        }
        
        if (parts.size() < 1) {
            error += ("The product must contain at least one part.");
        }
        
        if (sum > price) {
            error += ("The product price must be greater than the cost of it's parts.");
        }
        
        return error;
    }
}
