/*
 * Abstract class for the classes Inhouse and Outsourced
 */
package alexnewmanc482project.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author anewman
 */

public abstract class Part {
    // instance variables
    private final IntegerProperty partID;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    private final StringProperty name;
    private final DoubleProperty price;
    
    // constructor
    public Part(){
        partID = new SimpleIntegerProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();   
    }
    
    // accessor methods for simple objects and for data
    public StringProperty getNameProperty(){
        return name;
    }
    
    public DoubleProperty getPriceProperty(){
        return price;
    }
    
    public IntegerProperty getInStockProperty(){
        return inStock;
    }
    
    public IntegerProperty getMinProperty(){
        return min;
    }
    
    public IntegerProperty getMaxProperty(){
        return max;
    }
    
    public IntegerProperty getPartIDProperty(){
        return partID;
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
    
    public int getPartID(){
        return this.partID.get();
    }
    
    
    // mutator methods
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
    
    public void setPartID(int partID){
        this.partID.set(partID);
    }
    
    // part validation
    public static String isValidPart(String name, int min, int max, int inv, double price, String error) {
        if (name == null){
            error = error + "The name field is blank. ";
        }
        if (min > max) {
            error = error + "The inventory minimum must be greater than the maximum. ";
        }
        if (inv < 1) {
            error = error + "The inventory cannot be less than 1. ";
        }
        if (inv < min || inv > max) {
            error = error + "Inventory must be between the minimum and maximum values. ";
        }
        if (price <= 0) {
            error = error + "The price cannot be 0 or less. ";
        }  
        return error;
    }
    
    
}
