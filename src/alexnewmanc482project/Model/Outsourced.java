/*
 * For those parts that are built by our vendors
 */
package alexnewmanc482project.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author anewman
 */
public class Outsourced extends Part{
    // class variable
    private final StringProperty companyName;
    
    // constructor
    public Outsourced(){
        super();
        companyName = new SimpleStringProperty();
    }
        
    // accessor methods
    public String getCompanyName(){
        return this.companyName.get();
    }
        
    // mutator methods
    public void setCompanyName(String companyName){
        this.companyName.set(companyName);
    }
    
}
