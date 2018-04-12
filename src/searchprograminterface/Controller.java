
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import nobelprize.*;


/**
 * FXML Controller class for the main GUI 
 *
 * @author Arnoldo
 */
public class Controller implements Initializable {
    
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
        
        // Setting up colums
        
        // First Name Column
        TableColumn<Laureate, String> firstNameColumn = new TableColumn<>("FirstName");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Laureate, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Laureate, String> c) {
                return new SimpleStringProperty(c.getValue().getFirstName());
                }
        });
        
        // Last Name Column
        TableColumn<Laureate, String> surNameColumn = new TableColumn<>("SurName");
        surNameColumn.setMinWidth(100);
        surNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Laureate, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Laureate, String> c) {
                return new SimpleStringProperty(c.getValue().getSurName());
                }
        });
        
        // Born Year Column
        TableColumn<Laureate, String> bornColumn = new TableColumn<>("Born");
        bornColumn.setMinWidth(100);
        bornColumn.setCellValueFactory(new PropertyValueFactory<>("born"));
        
        // Died Year Column
        TableColumn<Laureate, String> diedColumn = new TableColumn<>("Died");
        diedColumn.setMinWidth(100);
        diedColumn.setCellValueFactory(new PropertyValueFactory<>("died"));
        
        // Country born Column
        TableColumn<Laureate, String> bornCountryColumn = new TableColumn<>("Country Born");
        bornCountryColumn.setMinWidth(100);
        bornCountryColumn.setCellValueFactory(new PropertyValueFactory<>("bornCountry"));
        
        // City Born Column
        TableColumn<Laureate, String> bornCityColumn = new TableColumn<>("City Born");
        bornCityColumn.setMinWidth(100);
        bornCityColumn.setCellValueFactory(new PropertyValueFactory<>("bornCity"));
        
        // Gender Column
        TableColumn<Laureate, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setMinWidth(100);
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        
        // Year prize recieved Column
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
        
        // Field for prize won
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
        
        // Motivation column
        TableColumn<Laureate, String> motivationColumn = new TableColumn<>("Motivation"); 
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
        
        // Affiliates column
        TableColumn<Laureate, String> affiliateNameColumn = new TableColumn<>("Affiliates");
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
        
        // add colums to the table to display data
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
    private void exitButtonClicked(ActionEvent event) throws IOException {
        Platform.exit();
    }
    
    // Accordion with selectable search queries 
    
    // Year Tab
    
     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */
    @FXML
    private Map<String, String> search1900(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 1900));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }    

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */
    @FXML
    private Map<String, String> search1910(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 1910));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */
    @FXML
    private Map<String, String> search1920(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 1920));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }  

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */    
    @FXML
    private Map<String, String> search1930(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 1930));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }   

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */    
    @FXML
    private Map<String, String> search1940(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 1940));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */    
    @FXML
    private Map<String, String> search1950(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 1950));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */    
    @FXML
    private Map<String, String> search1960(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 1960));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */    
    @FXML
    private Map<String, String> search1970(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 1970));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */    
    @FXML
    private Map<String, String> search1980(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 1980));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */    
    @FXML
    private Map<String, String> search1990(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 1990));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */    
    @FXML
    private Map<String, String> search2000(ActionEvent event) {
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

     /**
     * When a year button is clicked, that year is added to the query dictionary.
     */    
    @FXML
    private Map<String, String> search2010(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in a decade
        searchResults.addAll(searchHandler.prizeByYear(allLaureates, 2010));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }
    
    //Gender Tab
    
    /**
     * When male button is clicked, male gender is added to the query dictionary.
     */
    @FXML
    private Map<String, String> searchMale(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners that are males
        searchResults.addAll(searchHandler.clarifyMale(allLaureates));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        
        laureateTable.setItems(data);
        laureateTable.refresh();
               
        return searchQuery;
    }

    /**
     * When Female button is clicked, Female gender is added to the query dictionary.
     */
    @FXML
    private Map<String, String> searchFemale(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners that are females
        searchResults.addAll(searchHandler.clarifyFemale(allLaureates));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }
    
    /**
     * When Organization button is clicked, organization is added to the query dictionary.
     * This "gender" category is for entries that aren't people.
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchOrganization(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners that are organizations
        searchResults.addAll(searchHandler.clarifyUngendered(allLaureates));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }
    
    // Prize Tab
    
    /**
     * When Physics button is pressed, Physics is added to the query dictionary 
     * with the key set as prize. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchPhysics(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in Physics
        searchResults.addAll(searchHandler.clarifyCategory(allLaureates, "physics"));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }
    
    /**
     * When Chemistry button is pressed, Chemistry is added to the query dictionary 
     * with the key set as prize.  
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchChemistry(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in chemistry
        searchResults.addAll(searchHandler.clarifyCategory(allLaureates, "chemistry"));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }
    
    /**
     * When Economics button is pressed, Economics is added to the query dictionary 
     * with the key set as prize. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchEconomics(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in economics
        searchResults.addAll(searchHandler.clarifyCategory(allLaureates, "economics"));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }

    /**
     * When Literature button is pressed, Literature is added to the query dictionary 
     * with the key set as prize. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchLiterature(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in literature
        searchResults.addAll(searchHandler.clarifyCategory(allLaureates, "literature"));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }
    
    /**
     * When Medicine button is pressed, Medicine is added to the query dictionary 
     * with the key set as prize. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchMedicine(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in medicine
        searchResults.addAll(searchHandler.clarifyCategory(allLaureates, "medicine"));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }
    
    /**
     * When Peace button is pressed, Peace is added to the query dictionary 
     * with the key set as prize. 
     * @return searchQuery - search query dictionary
     */
    @FXML
    private Map<String, String> searchPeace(ActionEvent event) {
        // ArrayList of all winners
        allLaureates = (ArrayList<Laureate>) searchHandler.getWinners();
        
        // ArraList of all winners in peace category
        searchResults.addAll(searchHandler.clarifyCategory(allLaureates, "peace"));
        
        data.clear();
        for(Laureate laureate: searchResults){
            data.add(laureate);
        }
        laureateTable.setItems(data);
               
        return searchQuery;
    }
    
    /**
     * When people enter a name in the search bar, all matches are added to the searchQuery.
     * @return searchQuery - containing all Laureates that match the name provide.
     */
    @FXML 
    private Map<String, String> searchText(){
        String userSearch = userIn.getText();
        
        //upper case first letter of user input for proper search in database
        //https://stackoverflow.com/questions/5725892/how-to-capitalize-the-first-letter-of-word-in-a-string-using-java
        try{
          userSearch = userSearch.substring(0, 1).toUpperCase() + userSearch.substring(1);
        }catch (StringIndexOutOfBoundsException e) {
        }
        searchResults = (ArrayList<Laureate>) searchHandler.getNameSearch().get(userSearch);
        if(searchResults == null){
            System.out.println("This is bad input");
        }else{
            data.clear();
            for(Laureate laureate: searchResults){
                data.add(laureate);
            }
            laureateTable.setItems(data);
        }
        return searchQuery;
    
    }
    
    /**
     * Double clicking a row will open a new window that shows detail of the 
     * person/organization selected from the table.
     * @param event - mouse click event
     * @throws IOException - throws exception if nothing/wrong search query entered.
     */
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
            Stage secondStage = new Stage();
            secondStage.setScene(personInfoScene);
            secondStage.show();       
        }
    }    
   
    
}
