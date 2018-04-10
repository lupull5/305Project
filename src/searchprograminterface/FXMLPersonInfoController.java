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
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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
        double layoutIncrementer = 1;
        for(Prize prize: laureate.getPrizes()){
            prizesPane.getChildren().add(buildPrize(prize));            
        }        
        for(Node pane: prizesPane.getChildren()){
            pane.setLayoutY(layoutIncrementer);
            layoutIncrementer += 110;
        }
        laureateImage.setImage(new Image(new File(laureate.getLaureateImage()).toURI().toString())); 
        
        
    }
    
    private Pane buildPrize(Prize prize){
        Collection<Text> textHolder = new ArrayList();
        
        textHolder.add(textBuilder("Prize Year:",12,30));
        textHolder.add(textBuilder("Category:",14,55));       
        textHolder.add(textBuilder("Motivation:",10,108));
        textHolder.add(textBuilder("Affiliate(s):",10,82));
        
        
        textHolder.add(textBuilder(prize.getYear().toString(), 104,30));        
        textHolder.add(textBuilder(prize.getCategory(), 104, 56));
        textHolder.add(textBuilder(prize.getMotivation(), 104, 109));
        textHolder.add(textBuilder(prize.getAffiliate(), 104, 82));
        Pane complete = new Pane();
        complete.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        complete.getChildren().addAll(textHolder);
        return complete;
    }
    
    private Text textBuilder(String text, Integer xLoc, Integer yLoc){
        Text newText = new Text(text);
        newText.setLayoutX(xLoc);
        newText.setLayoutY(yLoc);
        newText.setFont(Font.font("System", FontWeight.LIGHT, FontPosture.REGULAR, 16));
        return newText;
    }
}
