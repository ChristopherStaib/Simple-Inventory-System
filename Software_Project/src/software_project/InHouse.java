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
public class InHouse extends Part { 
    private int machineID;
    public InHouse(int partId, String partName, int partStock, double partPrice, int partMin, int partMax, int machineID) {
        super(partId, partName, partStock, partPrice, partMin, partMax);
        this.machineID = machineID;
    }
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
    
    public int getMachineID() {
        return machineID;
    }
}
