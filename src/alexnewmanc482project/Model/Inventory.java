/*
 * Contains lists of all products and parts
 */
package alexnewmanc482project.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author anewman
 */
public class Inventory {
    //class variables
    private static int partCount = 0;
    private static int prodCount = 0;
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    
    // constructor with test data
    public Inventory() {

    }
    
    // class methods
    
    // product methods
    
    public static void addProduct(Product prod) {
        products.add(prod);
    }
    
    public static boolean removeProduct(Product prod) {
        return products.remove(prod); 
    }
    
    public Product lookupProduct(int index) {
        return products.get(index);
    }
    
    public static void updateProduct(int index, Product prod) {
        products.set(index, prod);
    }
    
    public static ObservableList<Product> getProdInventory(){
        return products;
    }
    
    public static int getProdCount() {
        prodCount++;
        return prodCount;
    }
    
    public static int searchForProduct (String searchTerm) {
        boolean isFound = false;
        int index = 0;
        
        if (isInt(searchTerm)) {
            for (int i = 0; i <products.size(); i++) {
                if (Integer.parseInt(searchTerm) == products.get(i).getProductID()) {
                    index = i;
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < products.size(); i++) {
                if (searchTerm.equals(products.get(i).getName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        
        if (isFound = true) {
            return index;
        } else {
            System.out.println("No products with that name can be found.");
            return -1;
        }
    }
    
    public static boolean validatePartDel(Part part) {
        boolean isFound = false;
        for (int i=0; i < products.size(); i++) {
            if(products.get(i).getAssociatedParts().contains(part)) {
                isFound = true;
            }
        }
        return isFound;
    }
    
    //part methods
    
    public static void addPart(Part prt) {
        allParts.add(prt);
    }
    
    public static boolean deletePart(Part prt) {
        return allParts.remove(prt); 
    }
       
    public static void updatePart(int index, Part part){
        allParts.set(index, part);
    }

    
    public static ObservableList<Part> getPartData() {
        return allParts;
    }
    
    public static int getPartCount() {
        partCount++;
        return partCount;
    }
    
    public static int lookupPart(String searchTerm){
        boolean isFound = false;
        int index = 0;
        
        if (isInt(searchTerm)) {
            for (int i=0; i < allParts.size(); i++) {
                if (Integer.parseInt(searchTerm) == allParts.get(i).getPartID()) {
                    index = i;
                    isFound = true;
                }
            }
        } else {
            for (int i=0; i < allParts.size(); i++) {
                if (searchTerm.equals(allParts.get(i).getName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound = true) {
            return index;
        } else {
            System.out.println("No parts with that name are found.");
            return -1;
        }
    }
    
    
    //misc methods
    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
