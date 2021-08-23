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
public class AddProductScreenController implements Initializable {

    @FXML
    private Button addPart_SearchButton;
    
    //Textfields
    @FXML
    private TextField addProduct_Name;
    @FXML
    private TextField addProduct_ID;
    @FXML
    private TextField addProduct_Inv;
    @FXML
    private TextField addProduct_Price;
    @FXML
    private TextField addProduct_Max;
    @FXML
    private TextField addProduct_Min;
    @FXML
    private TextField addPart_SearchText;
    
    //allParts Table
    @FXML
    private TableView<Part> addProduct_allPartTable;
    @FXML
    private TableColumn<Part, Integer> allPartTable_ID;
    @FXML
    private TableColumn<Part, String> allPartTable_Name;
    @FXML
    private TableColumn<Part, Integer> allPartTable_Inv;
    @FXML
    private TableColumn<Part, Double> allPartTable_Price;
    
    //selectedPart or associatedParts Table
    @FXML
    private TableView<Part> addProduct_selectedPartTable;
    @FXML
    private TableColumn<Part, Integer> selectedPartTable_ID;
    @FXML
    private TableColumn<Part, String> selectedPartTable_Name;
    @FXML
    private TableColumn<Part, Integer> selectedPartTable_Inv;
    @FXML
    private TableColumn<Part, Double> selectedPartTable_Price;
    
    
        
    private ObservableList<Part> linkedParts = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set top and bottom table values for parts
        allPartTable_ID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        allPartTable_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartTable_Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartTable_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProduct_allPartTable.setItems(Inventory.getAllParts());
        
        selectedPartTable_ID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        selectedPartTable_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedPartTable_Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPartTable_Price.setCellValueFactory(new PropertyValueFactory<>("price"));    
    }    

    @FXML
    private void AddProductsSaveChanges(ActionEvent event) {
        /**********
        id is generated based on 2 conditions:
        1. if list of products is empty id will be 1
        2. if list of products has products, the id of the new product will be 1 + the 
        last product ID (for example, last product has ID of 4, then new product ID will be 5)   
        **********/
        
        int idGenerator;
        if (Inventory.getAllProducts().isEmpty()) {
              idGenerator = 0;
        }
        else {
            Product lastOfProducts = Inventory.getAllProducts().get(Inventory.getAllProducts().size()-1);
            idGenerator = lastOfProducts.getID(); 
        }
                           
        int id = idGenerator + 1;     
        String name = addProduct_Name.getText();
        int inv = Integer.parseInt(addProduct_Inv.getText());
        double price = Double.parseDouble(addProduct_Price.getText());
        int max = Integer.parseInt(addProduct_Max.getText());
        int min = Integer.parseInt(addProduct_Min.getText());     
        
        if (Inventory.getAllParts().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Please add a part to inventory before adding a product");
            alert.show();
        }
        else if(linkedParts.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Please select at least one part to associate with this product.");
            alert.show();
        }
        else { 
            //add new product to Inventory
            Product product = new Product(id, name, inv, price, max, min, linkedParts);
            Inventory.addProduct(product);

            //close window
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
     
    }
    
    @FXML
    private void AddProductsCancelChanges(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you would like to Cancel?");
        alert.showAndWait().ifPresent(response-> {
            if(response == ButtonType.OK) {  
                addProduct_selectedPartTable.getItems().clear();
        
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

                System.out.println("Add Products Window Closed.");
                
                }  
        });        
        
        
        
        
        
        
    }

    @FXML
    private void AddtoAssociatedList(ActionEvent event) {
        if(addProduct_allPartTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Please select a part from the list to associate with this product.");
            alert.show();
        }
        
        else {
            //selected Part of array is targeted
            Part selectedPart = addProduct_allPartTable.getSelectionModel().getSelectedItem();
            //add associatedpart here 
            linkedParts.add(selectedPart);
            //set selectedPartTable Items
            addProduct_selectedPartTable.setItems(linkedParts);
            //clear selection
            addProduct_allPartTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void RemoveFromAssociatedList(ActionEvent event) {
        if(addProduct_selectedPartTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Please select a part to delete from the list to dissociate from the product.");
            alert.show();
        }
        else {
            Part selectedPart = addProduct_selectedPartTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you would like to delete this part?");
        alert.showAndWait().ifPresent(response-> {
            if(response == ButtonType.OK) {  
                linkedParts.remove(selectedPart);
                addProduct_selectedPartTable.setItems(linkedParts);
                addProduct_selectedPartTable.getSelectionModel().clearSelection();
                }  
        });
            
        }
    }

    @FXML
    private void AddProductSearchPart(ActionEvent event) {
        /***********
        search uses a regular expression to determine if input is an integer or string first,
        then will display list of appropriately matched products. 
        if the search is empty, list will simply display all results, if the search is not found the table
        will display nothing.
        ***********/
        
        String partSearchText = addPart_SearchText.getText();
        System.out.println(partSearchText);
        
        //checks to see if textfield matches 0-9 in the first char or more
        if (partSearchText.matches("[0-9]+")) {
            int partSearchInt = Integer.parseInt(partSearchText);
            Part searchResultInt = Inventory.lookupPart(partSearchInt);
            ObservableList<Part> searchedParts = FXCollections.observableArrayList();
            if (searchResultInt != null) {
                searchedParts.add(searchResultInt);
                addProduct_allPartTable.setItems(searchedParts);
            } else {
                addPart_SearchText.clear();
                addPart_SearchText.setPromptText("No matches found.");
            }
        } else if (partSearchText.isEmpty()) {
            addProduct_allPartTable.setItems(Inventory.getAllParts());
            addPart_SearchText.clear();
            addPart_SearchText.setPromptText("Displaying all parts.");
        } else {
            ObservableList<Part> searchedPartsText = Inventory.lookupPart(partSearchText);

            if (searchedPartsText.isEmpty()) {
                addProduct_allPartTable.setItems(searchedPartsText);
                addPart_SearchText.clear();
                addPart_SearchText.setPromptText("No matches found.");
            } else {
                addProduct_allPartTable.setItems(searchedPartsText);
            }
        }
    }
    
}
