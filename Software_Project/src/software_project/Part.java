/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_project;

/**
 *
 * @author chris
 */
public abstract class Part {
    private int partID;
    private String partName;
    private double partPrice;
    private int partStock;
    private int partMin;
    private int partMax;
    
    
    //constructor
    public Part(int partId, String partName, int partStock, double partPrice, int partMin, int partMax){
        this.partID = partId;
        this.partName = partName;
        this.partStock = partStock;
        this.partPrice = partPrice;
        this.partMin = partMin;
        this.partMax = partMax;
    }
    
   
    //set methods
    public void setID(int id) {
        this.partID = id;
    }
    
    public void setName(String name) {
        this.partName = name;
    }
    
    public void setPrice (double price) {
        this.partPrice = price;
    }
    
    public void setStock(int stock) {
        this.partStock = stock;
    }
    
    public void setMin(int min) {
        this.partMin = min;
    }
    
    public void setMax(int max) {
        this.partMax = max;
    }
    
    public int getID() {
        return partID;
    }
    
    public String getName() {
        return partName;
    }
    
    public double getPrice() {
        return partPrice;
    }
    
    public int getStock() {
        return partStock;
    }
    
    public int getMin() {
        return partMin;
    }
    
    public int getMax() {
        return partMax;
    }
}
