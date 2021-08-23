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
public class AddPartScreenController implements Initializable {

    //Radio Buttons
    @FXML
    private ToggleGroup group1;
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outsourcedRadioButton;
    
    //Labels
    @FXML
    private Label addPartIDLabel;
    @FXML
    private Label addPartNameLabel;
    @FXML
    private Label addPartInvLabel;
    @FXML
    private Label addPartPriceLabel;
    @FXML
    private Label addPartMaxLabel;
    @FXML
    private Label addPartMachineIDLabel;
    @FXML
    private Label addPartMinLabel;
    @FXML
    private Label addPartCompanyNameLabel;
    
    //Textfields
    @FXML
    private TextField addPartMinTextfield;
    @FXML
    private TextField addPartCompanyNameTextfield;
    @FXML
    private TextField addPartIDTextfield;
    @FXML
    private TextField addPartNameTextfield;
    @FXML
    private TextField addPartInvTextfield;
    @FXML
    private TextField addPartPriceTextfield;
    @FXML
    private TextField addPartMaxTextfield;
    @FXML
    private TextField addPartMachineIDTextfield;
    
    //Buttons
    @FXML
    private Button AddPartSaveButton;
    @FXML
    private Button AddPartCancelButton;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AddPartsSaveChanges(ActionEvent event) {
        /**********
        id is generated based on 2 conditions:
        1. if list of parts is empty id will be 1
        2. if list of parts has parts, the id of the new part will be 1 + the 
        last parts ID (for example, last part has ID of 4, then new part ID will be 5)   
        **********/
        
        int idGenerator;
        if(Inventory.getAllParts().isEmpty()) {
            idGenerator = 0;
        }
        else {
            Part lastOfParts = Inventory.getAllParts().get(Inventory.getAllParts().size()-1);
            idGenerator = lastOfParts.getID();
        }
        
        
        
        int id = idGenerator + 1;
        String name = addPartNameTextfield.getText();
        int inv = Integer.parseInt(addPartInvTextfield.getText());
        double price = Double.parseDouble(addPartPriceTextfield.getText());
        int max = Integer.parseInt(addPartMaxTextfield.getText());
        int min = Integer.parseInt(addPartMinTextfield.getText());
        if(inHouseRadioButton.isSelected()) {
            int machineID = Integer.parseInt(addPartMachineIDTextfield.getText());
            Part part = new InHouse(id, name, inv, price, max, min, machineID);
            Inventory.addPart(part);
        }
        else {
            String companyName = addPartCompanyNameTextfield.getText();
            Part part = new Outsourced(id, name, inv, price, max, min, companyName);
            Inventory.addPart(part);
        }

        
        //close window
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void AddPartsCancelChanges(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you would like to Cancel?");
        alert.showAndWait().ifPresent(response-> {
            if(response == ButtonType.OK) {  
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

                System.out.println("Add Parts Window Closed."); 
                }  
        });

        
    }

    @FXML
    private void InHouseButtonSelected(ActionEvent event) {
        
        //hiding company name related label and textfield
        addPartCompanyNameLabel.setVisible(false);
        addPartCompanyNameTextfield.setVisible(false);
        
        //selecting inHouseRadioButton and setting bool to true for inHouse
        outsourcedRadioButton.setSelected(false);
        //inHouse = true;
        inHouseRadioButton.setSelected(true);
        
        //show machine ID label and textfield
        addPartMachineIDLabel.setVisible(true);
        addPartMachineIDTextfield.setVisible(true);
    }

    @FXML
    private void OutsourcedButtonSelected(ActionEvent event) {
        //show company name label and textfield
        addPartCompanyNameLabel.setVisible(true);
        addPartCompanyNameTextfield.setVisible(true);
        
        //selecting outsourcedRadioButton and setting bool to false for inHouse
        inHouseRadioButton.setSelected(false);
        //inHouse = false;
        outsourcedRadioButton.setSelected(true);
        
        //hiding machine label and textfield
        addPartMachineIDLabel.setVisible(false);
        addPartMachineIDLabel.setVisible(false);
    }
    
}
