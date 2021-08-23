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
public class Outsourced extends Part {
    private String companyName;
    public Outsourced(int partId, String partName, int partStock, double partPrice, int partMin, int partMax, String companyName) {
        super(partId, partName, partStock, partPrice, partMin, partMax);
        this.companyName = companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getCompanyName() {
        return companyName;
    }
}
