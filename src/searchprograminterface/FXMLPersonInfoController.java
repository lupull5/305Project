/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchprograminterface;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nobelprize.*;

/**
 * FXML Controller class
 *
 * @author arnoldom7
 */
public class FXMLPersonInfoController implements Initializable {
    
    private Laureate laureate;  
    @FXML private Text firstName;
    @FXML private Text surName;
    @FXML private Text born;
    @FXML private Text died;
    @FXML private Text bornCity;
    @FXML private Text bornCountry;
    @FXML private Text gender;
    @FXML private ImageView laureateImage;
    @FXML private Pane prizesPane;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

 
    }    

    public void initLaureate(Laureate laureate){
        this.laureate = laureate;
        firstName.setText(laureate.getFirstName());
        surName.setText(laureate.getSurName());
        born.setText(laureate.getBorn());
        died.setText(laureate.getDied());
        bornCity.setText(laureate.getBornCity());
        bornCountry.setText(laureate.getBornCountry());
        gender.setText(laureate.getGender());
        for(Prize prize: laureate.getPrizes()){
            prizesPane.getChildren().add(buildPrize(prize));
        }        
        laureateImage.setImage(new Image(new File(laureate.getLaureateImage()).toURI().toString()));                                   
    }
    
    private Pane buildPrize(Prize prize){
        Collection<Text> textHolder = new ArrayList();
        textHolder.add(new Text("Prize Year:"));
        textHolder.add(new Text("Category:"));       
        textHolder.add(new Text("Indicator:"));
        textHolder.add(new Text("Affiliate(s):"));
        
        
        textHolder.add(new Text(prize.getYear().toString()));        
        textHolder.add(new Text(prize.getCategory()));
        textHolder.add(new Text(prize.getMotivation()));
        textHolder.add(new Text(prize.getAffiliate()));
        Pane complete = new Pane();
        complete.getChildren().addAll(textHolder);
        return complete;
    }
}
