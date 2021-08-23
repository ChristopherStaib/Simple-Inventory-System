/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class ModifyProductScreenController implements Initializable {

    @FXML
    private Button modifyProductSearchButton;
    @FXML
    private TextField modifyProductSearchText;
    @FXML
    private TextField modifyProduct_ID;
    @FXML
    private TextField modifyProduct_Name;
    @FXML
    private TextField modifyProduct_Inv;
    @FXML
    private TextField modifyProduct_Price;
    @FXML
    private TextField modifyProduct_Max;
    @FXML
    private TextField modifyProduct_Min;
    @FXML
    private TableView<Part> modifyProduct_allPartTable;
    @FXML
    private TableColumn<Part, Integer> allPartTable_ID;
    @FXML
    private TableColumn<Part, String> allPartTable_Name;
    @FXML
    private TableColumn<Part, Integer> allPartTable_Inv;
    @FXML
    private TableColumn<Part, Double> allPartTable_Price;
    @FXML
    private TableView<Part> modifyProduct_selectedPartTable;
    @FXML
    private TableColumn<Part, Integer> selectedPartTable_ID;
    @FXML
    private TableColumn<Part, String> selectedPartTable_Name;
    @FXML
    private TableColumn<Part, Integer> selectedPartTable_Inv;
    @FXML
    private TableColumn<Part, Double> selectedPartTable_Price;

    
    private ObservableList<Part> modifiedParts = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void ModifyProductsSaveChanges(ActionEvent event) {
          int id = Integer.parseInt(modifyProduct_ID.getText());
          String name = modifyProduct_Name.getText();
          int inv = Integer.parseInt(modifyProduct_Inv.getText());
          double price = Double.parseDouble(modifyProduct_Price.getText());
          int max = Integer.parseInt(modifyProduct_Max.getText());
          int min = Integer.parseInt(modifyProduct_Min.getText());
          ObservableList<Part> linkedParts = modifiedParts;
          
          if(linkedParts.isEmpty()) {
              Alert alert = new Alert(AlertType.ERROR, "Please select at least one part to associate with this product.");
              alert.show();
          }
          else {
               Product product = new Product(id, name, inv, price, max, min, linkedParts);
               Inventory.updateProduct(id-1, product);

               final Node source = (Node) event.getSource();
               final Stage stage = (Stage) source.getScene().getWindow();
               stage.close();

               System.out.println("Product " + id + " has been modified.");   
          }
                 
    }

    public void RetrieveProduct (Product p) {
        
        modifyProduct_ID.setText(Integer.toString(p.getID()));
        modifyProduct_Name.setText(p.getName());
        modifyProduct_Inv.setText(Integer.toString(p.getStock()));
        modifyProduct_Price.setText(Double.toString(p.getPrice()));
        modifyProduct_Max.setText(Integer.toString(p.getMax()));
        modifyProduct_Min.setText(Integer.toString(p.getMin()));
        
        //generate top and bottom table here
        allPartTable_ID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        allPartTable_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartTable_Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartTable_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProduct_allPartTable.setItems(Inventory.getAllParts());
        
        selectedPartTable_ID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        selectedPartTable_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedPartTable_Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPartTable_Price.setCellValueFactory(new PropertyValueFactory<>("price")); 
        modifyProduct_selectedPartTable.setItems(p.getAllAssociatedParts());
        
        modifiedParts = p.getAllAssociatedParts();
        
    }
    
    @FXML
    private void ModifyProductsCancelChanges(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you would like to Cancel?");
        alert.showAndWait().ifPresent(response-> {
            if(response == ButtonType.OK) {  
                    final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();

                    System.out.println("Modify Products Aborted.");  
                }  
        });        
        
        
        
    }

    @FXML
    private void AddtoAssociatedList(ActionEvent event) {
        if(modifyProduct_allPartTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part from the list to associate with this product.");
            alert.show();
        }
        
        else {
            //selected Part of array is targeted
            Part selectedPart = modifyProduct_allPartTable.getSelectionModel().getSelectedItem();
            //add associatedpart here 
            modifiedParts.add(selectedPart);
            //set selectedPartTable Items
            modifyProduct_selectedPartTable.setItems(modifiedParts);
            //clear selection
            modifyProduct_allPartTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void RemoveFromAssociatedList(ActionEvent event) {
        if(modifyProduct_selectedPartTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to delete from the list to dissociate from the product.");
            alert.show();
        }
        else {

            Part selectedPart = modifyProduct_selectedPartTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you would like to delete this part?");
            alert.showAndWait().ifPresent(response-> {
            if(response == ButtonType.OK) {  
                modifiedParts.remove(selectedPart);
                modifyProduct_selectedPartTable.setItems(modifiedParts);
                }  
        });
            
        }
    }

    @FXML
    private void ModifyProductSearchPart(ActionEvent event) {
        
         /***********
        search uses a regular expression to determine if input is an integer or string first,
        then will display list of appropriately matched products. 
        if the search is empty, list will simply display all results, if the search is not found the table
        will display nothing.
        ***********/
         
         
        String partSearchText = modifyProductSearchText.getText();
        System.out.println(partSearchText);
        
        //checks to see if textfield matches 0-9 in the first char or more
        if (partSearchText.matches("[0-9]+")) {
            int partSearchInt = Integer.parseInt(partSearchText);
            Part searchResultInt = Inventory.lookupPart(partSearchInt);
            ObservableList<Part> searchedParts = FXCollections.observableArrayList();
            if (searchResultInt != null) {
                searchedParts.add(searchResultInt);
                modifyProduct_allPartTable.setItems(searchedParts);
            } else {
                modifyProductSearchText.clear();
                modifyProductSearchText.setPromptText("No matches found.");
            }
        } else if (partSearchText.isEmpty()) {
            modifyProduct_allPartTable.setItems(Inventory.getAllParts());
            modifyProductSearchText.clear();
            modifyProductSearchText.setPromptText("Displaying all parts.");
        } else {
            ObservableList<Part> searchedPartsText = Inventory.lookupPart(partSearchText);

            if (searchedPartsText.isEmpty()) {
                modifyProduct_allPartTable.setItems(searchedPartsText);
                modifyProductSearchText.clear();
                modifyProductSearchText.setPromptText("No matches found.");
            } else {
                modifyProduct_allPartTable.setItems(searchedPartsText);
            }
        }
    }
    
}
