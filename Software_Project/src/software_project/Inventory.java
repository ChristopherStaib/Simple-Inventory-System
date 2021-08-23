/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author chris
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partID) {
        for (int i = 0; i < allParts.size(); i++) {
            if (partID == allParts.get(i).getID()){
                return allParts.get(i);
            }
        }
        return null;
    }
    
    public static Product lookupProduct(int productID) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (productID == allProducts.get(i).getID()){
                return allProducts.get(i);
            }
        }
        return null;
    }
    
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchedList = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                searchedList.add(part);
            }
        }
        return searchedList;
    }
    
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchedList = FXCollections.observableArrayList();
        for (Product product : allProducts) {   
            if (product.getName().contains(productName)) {
                searchedList.add(product);
            }
        } 
        return searchedList;
        
    }
    
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    
    
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }
    
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
                allParts.remove(selectedPart);
                return true;
            }
            else {
                return false;
            }
    }
    
    public static boolean deleteProduct(Product selectedProduct) {
         if (allProducts.contains(selectedProduct)) {
                allProducts.remove(selectedProduct);
                return true;
            }
            else {
                return false;
            }
    }
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
