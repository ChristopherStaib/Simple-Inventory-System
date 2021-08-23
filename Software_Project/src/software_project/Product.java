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
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private double productPrice;
    private int productStock;
    private int productMin;
    private int productMax;
    
    
    //constructor
    public Product(int productID, String productName, int productStock, double productPrice,  int productMin, int productMax, ObservableList<Part> associatedParts) {
        this.productID = productID;
        this.productName = productName;
        this.productStock = productStock;
        this.productPrice = productPrice;
        this.productMin = productMin;
        this.productMax = productMax;
        this.associatedParts = associatedParts;
    }
    
    //setters
    public void setID(int id) {
        this.productID = id;
    }
    
    public void setName(String name) {
        this.productName = name;
    }
    
    public void setPrice(double price) {
        this.productPrice = price;
    }
    
    public void setStock(int stock) {
        this.productStock = stock;    
    }
    
    public void setMin(int min) {
        this.productMin = min;
    }
    
    public void setMax(int max) {
        this.productMax = max;
    }
    
    //getters
    public int getID() {
        return productID;
    }
    
    public String getName() {
        return productName;
    }
    
    public double getPrice() {
        return productPrice;
    }
    
    public int getStock() {
        return productStock;
    }
    
    public int getMin() {
        return productMin;
    }
    
    public int getMax() {
        return productMax;
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
            if (associatedParts.contains(selectedAssociatedPart)) {
                associatedParts.remove(selectedAssociatedPart);
                return true;
            }
            else {
                return false;
            }
    }
    
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    
}
