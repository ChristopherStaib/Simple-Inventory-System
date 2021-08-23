/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class ModifyPartScreenController implements Initializable {

    //Radio Buttons
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private ToggleGroup group2;
    @FXML
    private RadioButton outsourcedRadioButton;
    
    //Labels
    @FXML
    private Label modifyPartIDLabel;
    @FXML
    private Label modifyPartNameLabel;
    @FXML
    private Label modifyPartInvLabel;
    @FXML
    private Label modifyPartPriceLabel;
    @FXML
    private Label modifyPartMaxLabel;
    @FXML
    private Label modifyPartMachineIDLabel;
    @FXML
    private Label modifyPartMinLabel;
    @FXML
    private Label modifyPartCompanyNameLabel;
    
    //Textfields
    @FXML
    private TextField modifyPartIDTextfield;
    @FXML
    private TextField modifyPartNameTextfield;
    @FXML
    private TextField modifyPartInvTextfield;
    @FXML
    private TextField modifyPartPriceTextfield;
    @FXML
    private TextField modifyPartMaxTextfield;
    @FXML
    private TextField modifyPartMachineIDTextfield;
    @FXML
    private TextField modifyPartMinTextfield;
    @FXML
    private TextField modifyPartCompanyNameTextfield;
    
    //Buttons
    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private Button modifyPartCancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void RetrievePart (Part p) {
        //set textxfields for retrieved part
        modifyPartIDTextfield.setText(Integer.toString(p.getID()));
        modifyPartNameTextfield.setText(p.getName());
        modifyPartInvTextfield.setText(Integer.toString(p.getStock()));
        modifyPartPriceTextfield.setText(Double.toString(p.getPrice()));
        modifyPartMaxTextfield.setText(Integer.toString(p.getMax()));
        modifyPartMinTextfield.setText(Integer.toString(p.getMin()));
        
        //if condition to appropriately set up form for InHouse vs Outsourced
        if (p instanceof InHouse) {
        modifyPartCompanyNameLabel.setVisible(false);
        modifyPartCompanyNameTextfield.setVisible(false);
         
        outsourcedRadioButton.setSelected(false);
        inHouseRadioButton.setSelected(true);
        
        modifyPartMachineIDLabel.setVisible(true);
        modifyPartMachineIDTextfield.setVisible(true);
        
        modifyPartMachineIDTextfield.setText(Integer.toString(((InHouse) p).getMachineID()));
        }
        else {
        modifyPartCompanyNameLabel.setVisible(true);
        modifyPartCompanyNameTextfield.setVisible(true);
        
        inHouseRadioButton.setSelected(false);
        outsourcedRadioButton.setSelected(true);
        
        modifyPartMachineIDLabel.setVisible(false);
        modifyPartMachineIDLabel.setVisible(false);
        
        modifyPartCompanyNameTextfield.setText(((Outsourced) p).getCompanyName());
        }
    }
    
    @FXML
    private void ModifyPartsSaveChanges(ActionEvent event) {

            int id = Integer.parseInt(modifyPartIDTextfield.getText());
            String name = modifyPartNameTextfield.getText();
            int inv = Integer.parseInt(modifyPartInvTextfield.getText());
            double price = Double.parseDouble(modifyPartPriceTextfield.getText());
            int max = Integer.parseInt(modifyPartMaxTextfield.getText());
            int min = Integer.parseInt(modifyPartMinTextfield.getText());  
            
            if (inHouseRadioButton.isSelected()) {
                Part part = new InHouse(id, name, inv, price, max, min, Integer.parseInt(modifyPartMachineIDTextfield.getText()));
                Inventory.updatePart(id-1, part);
                
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            
                System.out.println(id + " has been modified as an InHouse Part.");
            }
            else{
                Part part = new Outsourced(id, name, inv, price, max, min, modifyPartCompanyNameTextfield.getText());
                Inventory.updatePart(id-1, part);

                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            
                System.out.println(id + " has been modified as an Outsourced Part.");
            }
    }

    
    @FXML
    private void ModifyPartsCancelChanges(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you would like to Cancel?");
        alert.showAndWait().ifPresent(response-> {
            if(response == ButtonType.OK) {  
                    final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();

                    System.out.println("Modify Parts Window Closed.");
                }  
        });        
        
        
        
        
    }

    @FXML
    private void InHouseButtonSelected(ActionEvent event) {
        //hiding company name related label and textfield
        modifyPartCompanyNameLabel.setVisible(false);
        modifyPartCompanyNameTextfield.setVisible(false);
        
        //selecting inHouseRadioButton and setting bool to true for inHouse
        outsourcedRadioButton.setSelected(false);
        inHouseRadioButton.setSelected(true);
        
        //show machine ID label and textfield
        modifyPartMachineIDLabel.setVisible(true);
        modifyPartMachineIDTextfield.setVisible(true);
    }

    
    
    @FXML
    private void OutsourcedButtonSelected(ActionEvent event) {
        //show company name label and textfield
        modifyPartCompanyNameLabel.setVisible(true);
        modifyPartCompanyNameTextfield.setVisible(true);
        
        //selecting outsourcedRadioButton and setting bool to false for inHouse
        inHouseRadioButton.setSelected(false);
        outsourcedRadioButton.setSelected(true);
        
        //hiding machine label and textfield
        modifyPartMachineIDLabel.setVisible(false);
        modifyPartMachineIDLabel.setVisible(false);
    }
    
}
