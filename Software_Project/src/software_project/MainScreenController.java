/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author chris
 */
public class MainScreenController implements Initializable {

    
    @FXML
    private AnchorPane AnchorPane_Main;
    
    //Labels
    @FXML
    private Label label;
    @FXML
    private Label label_parts;
    @FXML
    private Label label_products;
    

    
    //Textfields
    @FXML
    private TextField textfield_parts;
    @FXML
    private TextField textfield_products;
    
    //Part Table
    @FXML
    private TableView<Part> table_parts;
    @FXML
    private TableColumn<Part, Integer> part_ID;
    @FXML
    private TableColumn<Part, String> part_Name;
    @FXML
    private TableColumn<Part, Integer> part_Inventory;
    @FXML
    private TableColumn<Part, Double> part_Price;
    
    //Buttons
    @FXML
    private Button button_parts_search;
    @FXML
    private Button button_products_search;
    @FXML
    private Button button_parts_add;
    @FXML
    private Button button_parts_modify;
    @FXML
    private Button button_parts_delete;
    @FXML
    private Button button_products_add;
    @FXML
    private Button button_products_modify;
    @FXML
    private Button button_products_delete;
    @FXML
    private Button button_exit;
    
    //Products Table
    @FXML
    private TableView<Product> table_products;
    @FXML
    private TableColumn<Product, Integer> product_ID;
    @FXML
    private TableColumn<Product, String> product_Name;
    @FXML
    private TableColumn<Product, Integer> product_Inventory;
    @FXML
    private TableColumn<Product, Double> product_Price;

    
    //Actions and Methods
    @FXML
    private void PartsSearchButtonAction(ActionEvent event) {
        /***********
        search uses a regular expression to determine if input is an integer or string first,
        then will display list of appropriately matched parts. 
        if the search is empty, list will simply display all results, if the search is not found the table
        will display nothing.
        ***********/
        
        String partSearchText = textfield_parts.getText();
        System.out.println(partSearchText);
        
        //checks to see if textfield matches 0-9 in the first char or more
        if (partSearchText.matches("[0-9]+")) {
            int partSearchInt = Integer.parseInt(partSearchText);
            Part searchResultInt = Inventory.lookupPart(partSearchInt);
            ObservableList<Part> searchedParts = FXCollections.observableArrayList();
            if (searchResultInt != null) {
                searchedParts.add(searchResultInt);
                table_parts.setItems(searchedParts);
            } else {
                textfield_parts.clear();
                textfield_parts.setPromptText("No matches found.");
            }
        } else if (partSearchText.isEmpty()) {
            table_parts.setItems(Inventory.getAllParts());
            textfield_parts.clear();
            textfield_parts.setPromptText("Displaying all parts.");
        } else {
            ObservableList<Part> searchedPartsText = Inventory.lookupPart(partSearchText);

            if (searchedPartsText.isEmpty()) {
                table_parts.setItems(searchedPartsText);
                textfield_parts.clear();
                textfield_parts.setPromptText("No matches found.");
            } else {
                table_parts.setItems(searchedPartsText);
            }
        }
    }

    @FXML
    private void ProductsSearchButtonAction(ActionEvent event) {
        /***********
        search uses a regular expression to determine if input is an integer or string first,
        then will display list of appropriately matched products. 
        if the search is empty, list will simply display all results, if the search is not found the table
        will display nothing.
        ***********/

        String productSearchText = textfield_products.getText();
        System.out.println(productSearchText);
        
        //checks to see if textfield matches 0-9 in the first char or more
        if (productSearchText.matches("[0-9]+")) {
            int productSearchInt = Integer.parseInt(productSearchText);
            Product searchResultInt = Inventory.lookupProduct(productSearchInt);
            ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
            if (searchResultInt != null) {
                searchedProducts.add(searchResultInt);
                table_products.setItems(searchedProducts);
            } else {
                textfield_products.clear();
                textfield_products.setPromptText("No matches found.");
            }
        } else if (productSearchText.isEmpty()) {
            table_products.setItems(Inventory.getAllProducts());
            textfield_products.clear();
            textfield_products.setPromptText("Displaying all products.");
        } else {
            ObservableList<Product> searchedProductsText = Inventory.lookupProduct(productSearchText);
            
            if (searchedProductsText.isEmpty()) {
                table_products.setItems(searchedProductsText);
                textfield_products.clear();
                textfield_products.setPromptText("No matches found.");
            } else {               
                table_products.setItems(searchedProductsText);
            }
        }
    }

    @FXML
    private void PartsAddButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPartScreen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Inventory Management System - Add Part");
        stage.setScene(new Scene(root1));
        stage.show();
        System.out.println("Add Parts Button Pressed");
    }

    @FXML
    private void PartsModifyButtonAction(ActionEvent event) throws IOException {
        if (table_parts.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "A Part must be selected to modify. Please Try Again.");
            alert.show();
        }
        else {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyPartScreen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        ModifyPartScreenController controller = fxmlLoader.getController();
        controller.RetrievePart(table_parts.getSelectionModel().getSelectedItem());
        table_parts.getSelectionModel().clearSelection(); 
        
        Stage stage = new Stage();
        stage.setTitle("Inventory Management System - Modify Part");
        stage.setScene(new Scene(root1));
        stage.show();
        System.out.println("Modify Parts Button Pressed");
        }
    }

    @FXML
    private void PartsDeleteButtonAction(ActionEvent event) throws IOException {
        if (table_parts.getSelectionModel().isEmpty()) {
           Alert alert = new Alert(AlertType.ERROR, "A part must be selected to delete. Please Try Again.");
           alert.show();
       }
       else {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you would like to delete this part?");
             alert.showAndWait().ifPresent(response-> {
            if(response == ButtonType.OK) {  
                  Inventory.deletePart(table_parts.getSelectionModel().getSelectedItem());
                  System.out.println("Part deleted.");
                }  
        });   
            
           
       }
    }

    @FXML
    private void ProductsAddButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddProductScreen.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Inventory Management System - Add Product");
        stage.setScene(new Scene(root1));
        stage.show();
        System.out.println("Add Product button pressed");
    }

    @FXML
    private void ProductsModifyButtonAction(ActionEvent event) throws IOException {
        if (table_products.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "A Product must be selected to modify. Please Try Again.");
            alert.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyProductScreen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            ModifyProductScreenController controller = fxmlLoader.getController();
            controller.RetrieveProduct(table_products.getSelectionModel().getSelectedItem());
            table_products.getSelectionModel().clearSelection();

            Stage stage = new Stage();
            stage.setTitle("Inventory Management System - Modify Product");
            stage.setScene(new Scene(root1));
            stage.show();
            System.out.println("Modify Products Button pressed");
        }
    }

    @FXML
    private void ProductsDeleteButtonAction(ActionEvent event) {
       if (table_products.getSelectionModel().isEmpty()) {
           Alert alert = new Alert(AlertType.ERROR, "A product must be selected to delete. Please Try Again.");
           alert.show();
       }
       else {
             Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you would like to delete this product?");
             alert.showAndWait().ifPresent(response-> {
            if(response == ButtonType.OK) {  
                  Inventory.deleteProduct(table_products.getSelectionModel().getSelectedItem());
                  System.out.println("Product deleted.");
                }  
        });    
           
       }
       
    }

    @FXML
    private void ExitButtonAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you would like to Exit?");
        alert.showAndWait().ifPresent(response-> {
            if(response == ButtonType.OK) {  
                System.out.println("Terminating Project...");
                Platform.exit();     
                }  
        });        
        
        
        
    }

        
        
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //test lists for main screen
        ObservableList<Part> testProductPartsList1 = FXCollections.observableArrayList();
        ObservableList<Part> testProductPartsList2 = FXCollections.observableArrayList();
        
        //generating new parts for main screen
        Part inHouse1 = new InHouse(1, "Part 1", 10, 19.99, 50, 10, 72);
        Part inHouse2 = new InHouse(2, "Part 2", 10, 19.99, 50, 10, 73);
        Part outsourced1 = new Outsourced(3, "Part 3", 10, 19.99, 50, 10, "Company 1");
        Part outsourced2 = new Outsourced(4, "Part 4", 10, 19.99, 50, 10, "Company 2");
        
        //adding parts to static inventory
        Inventory.addPart(inHouse1);
        Inventory.addPart(inHouse2);
        Inventory.addPart(outsourced1);
        Inventory.addPart(outsourced2);
        
        //adding to testproductspartslists based on inventory
        testProductPartsList1.addAll(Inventory.getAllParts());
        testProductPartsList2.add(Inventory.getAllParts().get(0));
        testProductPartsList2.add(Inventory.getAllParts().get(2));
        
        //creating new products
        Product product1 = new Product(1, "Product 1", 10, 19.99, 50, 10, testProductPartsList1);
        Product product2 = new Product(2, "Product 2", 10, 19.99, 50, 1, testProductPartsList1);
        Product product5 = new Product(5, "Product 2", 10, 11.99, 25, 5, testProductPartsList2);
        Product product3 = new Product(3, "Product 3", 10, 19.99, 50, 10, testProductPartsList1);
        Product product4 = new Product(4, "Product 4", 10, 19.99, 50, 10, testProductPartsList2);
        
        //adding products to static inventory
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        Inventory.addProduct(product5);
        
        

        //set up parts and products tables to display lists
        part_ID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        part_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        part_Inventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        part_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        table_parts.setItems(Inventory.getAllParts());

        product_ID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        product_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        product_Inventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        product_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        table_products.setItems(Inventory.getAllProducts());

    }

}
