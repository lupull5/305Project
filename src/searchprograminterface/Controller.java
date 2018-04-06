/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchprograminterface;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import nobelprize.*;


/**
 * FXML Controller class
 *
 * @author arnol
 */
public class Controller implements Initializable {

    @FXML
    private Color x2;
    @FXML
    private Font x1;
    
    @FXML private Accordion categories;
    @FXML private TitledPane year;
    @FXML private TitledPane gender;
    @FXML private TitledPane prize;
    @FXML private Button male; 
    @FXML private Button female; 
    @FXML private TextArea results;
    @FXML private TextField userIn;
    
    @FXML private TableView<Laureate> laureateTable = new TableView();      
    @FXML private TableColumn<Laureate, String> diedColumn;
    @FXML private TableColumn<Laureate, String> bornCountryColumn;
    @FXML private TableColumn<Laureate, String> bornCityColumn;
    @FXML private TableColumn<Laureate, String> genderColumn;
    @FXML private TableColumn<Laureate, String> prizeYearColumn;
    @FXML private TableColumn<Laureate, String> prizeCategoryColumn;
    @FXML private TableColumn<Laureate, String> motivationColumn;
    @FXML private TableColumn<Laureate, String> affiliateNameColumn;
    
    private ObservableList<Laureate> data = FXCollections.observableArrayList();
    ArrayList<Laureate> searchResults = new ArrayList();
    ArrayList<Laureate> allLaureates = new ArrayList();
    LaureateDatabase MrDataBase; 
    NarrowSearch searchHandler;
 
    
    static Map<String, String> searchQuery = new HashMap<>();

    public Controller() throws IOException {
        this.MrDataBase = new LaureateDatabase();
        this.searchHandler = new NarrowSearch(MrDataBase.getLaureateList());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Laureate, String> firstNameColumn = new TableColumn<>("FirstName");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Laureate, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Laureate, String> c) {
                return new SimpleStringProperty(c.getValue().getFirstName());
                }

        });       
        TableColumn<Laureate, String> surNameColumn = new TableColumn<>("SurName");
        surNameColumn.setMinWidth(100);
        surNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Laureate, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Laureate, String> c) {
                return new SimpleStringProperty(c.getValue().getSurName());
                }
        }); 
        TableColumn<Laureate, String> bornColumn = new TableColumn<>("Born");
        bornColumn.setMinWidth(100);
        bornColumn.setCellValueFactory(new PropertyValueFactory<>("born"));
        TableColumn<Laureate, String> diedColumn = new TableColumn<>("Died");
        diedColumn.setMinWidth(100);
        diedColumn.setCellValueFactory(new PropertyValueFactory<>("died"));
        TableColumn<Laureate, String> bornCountryColumn = new TableColumn<>("Country Born");
        bornCountryColumn.setMinWidth(100);
        bornCountryColumn.setCellValueFactory(new PropertyValueFactory<>("bornCountry"));
        TableColumn<Laureate, String> bornCityColumn = new TableColumn<>("City Born");
        bornCityColumn.setMinWidth(100);
        bornCityColumn.setCellValueFactory(new PropertyValueFactory<>("bornCity"));
        TableColumn<Laureate, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setMinWidth(100);
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        TableColumn<Laureate, String> prizeYearColumn = new TableColumn<>("Year Recieved");
        prizeYearColumn.setMinWidth(100);
        prizeYearColumn.setCellValueFactory(new Callback<CellDataFeatures<Laureate, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Laureate, String> c) {
                ArrayList<Integer> prizeYears = new ArrayList();
                for (Prize years: c.getValue().getPrizes()){
                    prizeYears.add(years.getYear());
                }
                return new SimpleStringProperty(prizeYears.toString());
            }
        });
        TableColumn<Laureate, String> prizeCategoryColumn = new TableColumn<>("Category");
        prizeCategoryColumn.setMinWidth(100);
        prizeCategoryColumn.setCellValueFactory(new Callback<CellDataFeatures<Laureate, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Laureate, String> c) {
                ArrayList<String> prizeYears = new ArrayList();
                for (Prize years: c.getValue().getPrizes()){
                    prizeYears.add(years.getCategory());
                }
                return new SimpleStringProperty(prizeYears.toString());
            }
        });    
        TableColumn<Laureate, String> motivationColumn = new TableColumn<>("Category");  //YOu might wanna vedo this one
        motivationColumn.setMinWidth(100);
        motivationColumn.setCellValueFactory(new Callback<CellDataFeatures<Laureate, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Laureate, String> c) {
                ArrayList<String> prizeYears = new ArrayList();
                for (Prize years: c.getValue().getPrizes()){
                    prizeYears.add(years.getMotivation());
                }
                return new SimpleStringProperty(prizeYears.toString());
            }
        });
        TableColumn<Laureate, String> affiliateNameColumn = new TableColumn<>("Affiliates");  //YOu might wanna vedo this one
        affiliateNameColumn.setMinWidth(100);
        affiliateNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Laureate, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Laureate, String> c) {
                ArrayList<String> prizeYears = new ArrayList();
                for (Prize years: c.getValue().getPrizes()){
                    prizeYears.add(years.getAffiliate());
                }
                return new SimpleStringProperty(prizeYears.toString());
            }
        });
        
        
        laureateTable.getColumns().addAll(
                firstNameColumn,
                surNameColumn,
                bornColumn,
                diedColumn,
                bornCountryColumn,
                bornCityColumn,
                genderColumn,
                prizeYearColumn,
                prizeCategoryColumn,
                motivationColumn,
                affiliateNameColumn
        );
    }    

    @FXML
    private void searchButtonClicked(ActionEvent event) {
        System.out.println("Searching...");
    }
    
    @FXML
    private void exitButtonClicked(ActionEvent event) throws IOException {
        Platform.exit();
    }
    
    // Accordion with selectable search queries 
    
    // Year Tab
    
    @FXML
    private Map<String, String> search1900(ActionEvent event) {
        
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 2000));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }    

    @FXML
    private Map<String, String> search1910(ActionEvent event) {
        if(!searchQuery.containsValue("1910")){
            searchQuery.put("Year", "1910");
            System.out.println("Will search for decade 1910s");
        }else{
            searchQuery.remove("Year", "1910");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    @FXML
    private Map<String, String> search1920(ActionEvent event) {
        if(!searchQuery.containsValue("1920")){
            searchQuery.put("Year", "1920");
            System.out.println("Will search for decade 1920s");
        }else{
            searchQuery.remove("Year", "1920");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }  

    @FXML
    private Map<String, String> search1930(ActionEvent event) {
        if(!searchQuery.containsValue("1930")){
            searchQuery.put("Year", "1930");
            System.out.println("Will search for decade 1930s");
        }else{
            searchQuery.remove("Year", "1930");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }   
    
    @FXML
    private Map<String, String> search1940(ActionEvent event) {
        if(!searchQuery.containsValue("1940")){
            searchQuery.put("Year", "1940");
            System.out.println("Will search for decade 1940s");
        }else{
            searchQuery.remove("Year", "1940");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    @FXML
    private Map<String, String> search1950(ActionEvent event) {
        if(!searchQuery.containsValue("1950")){
            searchQuery.put("Year", "1950");
            System.out.println("Will search for decade 1950s");
        }else{
            searchQuery.remove("Year", "1950");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    @FXML
    private Map<String, String> search1960(ActionEvent event) {
        if(!searchQuery.containsValue("1960")){
            searchQuery.put("Year", "1960");
            System.out.println("Will search for decade 1960s");
        }else{
            searchQuery.remove("Year", "1960");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    @FXML
    private Map<String, String> search1970(ActionEvent event) {
        if(!searchQuery.containsValue("1970")){
            searchQuery.put("Year", "1970");
            System.out.println("Will search for decade 1970s");
        }else{
            searchQuery.remove("Year", "1970");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    @FXML
    private Map<String, String> search1980(ActionEvent event) {
        if(!searchQuery.containsValue("1980")){
            searchQuery.put("Year", "1980");
            System.out.println("Will search for decade 1980s");
        }else{
            searchQuery.remove("Year", "1980");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    @FXML
    private Map<String, String> search1990(ActionEvent event) {
        if(!searchQuery.containsValue("1990")){
            searchQuery.put("Year", "1990");
            System.out.println("Will search for decade 1990s");
        }else{
            searchQuery.remove("Year", "1990");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    @FXML
    private Map<String, String> search2000(ActionEvent event) {
        if(!searchQuery.containsValue("2000")){
            searchQuery.put("Year", "2000");
            System.out.println("Will search for decade 2000s");
        }else{
            searchQuery.remove("Year", "2000");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    //Gender Tab
    
    /**
     * When male button is clicked, male gender is added to the query dictionary.
     * If male button is clicked again, male will be removed from search query.
     */
    @FXML
    private Map<String, String> searchMale(ActionEvent event) {
        if(!searchQuery.containsValue("Male")){
            searchQuery.put("Gender", "Male");
            System.out.println("Will search for male");
        }else{
            searchQuery.remove("Gender", "Male");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }

    /**
     * When Female button is clicked, Female gender is added to the query dictionary.
     * If Female button is clicked again, Female will be removed from search query.
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchFemale(ActionEvent event) {
        if(!searchQuery.containsValue("Female")){
            searchQuery.put("Gender", "Female");
            System.out.println("Will search for Female");
        }else{
            searchQuery.remove("Gender", "Female");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    // Prize Tab
    
    /**
     * When Physics button is pressed, Physics is added to the query dictionary 
     * with the key set as prize. If clicked again, Physics would be removed from 
     * query dictionary. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchPhysics(ActionEvent event) {
        if(!searchQuery.containsValue("Physics")){
            searchQuery.put("Prize", "Physics");
            System.out.println("Will search for Physics");
        }else{
            searchQuery.remove("Prize", "Physics");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    /**
     * When Chemistry button is pressed, Chemistry is added to the query dictionary 
     * with the key set as prize. If clicked again, Chemistry would be removed from 
     * query dictionary. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchChemistry(ActionEvent event) {
        if(!searchQuery.containsValue("Chemistry")){
            searchQuery.put("Prize", "Chemistry");
            System.out.println("Will search for Chemistry");
        }else{
            searchQuery.remove("Prize", "Chemistry");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    /**
     * When Economics button is pressed, Economics is added to the query dictionary 
     * with the key set as prize. If clicked again, Economics would be removed from 
     * query dictionary. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchEconomics(ActionEvent event) {
        if(!searchQuery.containsValue("Economics")){
            searchQuery.put("Prize", "Economics");
            System.out.println("Will search for Economics");
        }else{
            searchQuery.remove("Prize", "Economics");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }

    /**
     * When Literature button is pressed, Literature is added to the query dictionary 
     * with the key set as prize. If clicked again, Literature would be removed from 
     * query dictionary. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchLiterature(ActionEvent event) {
        if(!searchQuery.containsValue("Literature")){
            searchQuery.put("Prize", "Literature");
            System.out.println("Will search for Literature");
        }else{
            searchQuery.remove("Prize", "Literature");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    /**
     * When Medicine button is pressed, Medicine is added to the query dictionary 
     * with the key set as prize. If clicked again, Medicine would be removed from 
     * query dictionary. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchMedicine(ActionEvent event) {
        if(!searchQuery.containsValue("Medicine")){
            searchQuery.put("Prize", "Medicine");
            System.out.println("Will search for Medicine");
        }else{
            searchQuery.remove("Prize", "Medicine");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }
    
    /**
     * When Peace button is pressed, Peace is added to the query dictionary 
     * with the key set as prize. If clicked again, Peace would be removed from 
     * query dictionary. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchPeace(ActionEvent event) {
        if(!searchQuery.containsValue("Peace")){
            searchQuery.put("Prize", "Peace");
            System.out.println("Will search for Peace");
        }else{
            searchQuery.remove("Prize", "Peace");
        }
        System.out.println("Dictionary:" + searchQuery);
        return searchQuery;
    }

    @FXML 
    private Map<String, String> searchText(){
        String userSearch = userIn.getText();
        //searchQuery.put("firstname", userSearch);
        searchResults = (ArrayList<Laureate>) searchHandler.getNameSearch().get(userSearch);
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
        
        
        //results.setText(searchResults.get(0).getLaureateImage());
        Iterator<Laureate> peter = searchResults.iterator();
        while (peter.hasNext()){
            Laureate tom = peter.next();
            System.out.println(tom.getBorn());
        }
        
        return searchQuery;
    
    }
    
    @FXML
    private void selectedRow(MouseEvent event) throws IOException {
        Laureate row = laureateTable.getSelectionModel().getSelectedItem();
        

        
        if (row == null) {
            return;
        }else if(event.getClickCount() == 2){
            // open new window
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLPersonInfo.fxml"));
        
        Parent tableViewParent = loader.load();
        
        Scene personInfoScene = new Scene(tableViewParent);
                
            FXMLPersonInfoController controller = loader.getController();
            controller.initLaureate(row);
            Stage app_stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            app_stage.setScene(personInfoScene);
            app_stage.show();       
        }
        
 
        /**if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                System.out.println("Hello");                   
            }*/
        }    
   
    
    @FXML
    private void searchResults(){
        results.setText("Hello");
    }
}
