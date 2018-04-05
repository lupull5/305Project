/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchprograminterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        laureateImage.setImage(new Image(getClass().getResource(laureate.getLaureateImage()).toExternalForm()));
    }
}
