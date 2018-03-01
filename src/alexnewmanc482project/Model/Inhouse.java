/*
 * This is for those parts that are built in-house
 */
package alexnewmanc482project.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author anewman
 */

public class Inhouse extends Part{
    // class variable
    private final IntegerProperty machineID;
    
    // constructors
    public Inhouse(){
        super();
        machineID = new SimpleIntegerProperty();
    }
    
    // accessor methods
    public int getMachineID(){
        return this.machineID.get();
    }
    
    // mutator methods
    public void setMachineID(int machineID){
        this.machineID.set(machineID);
    }
    
}
